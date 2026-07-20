package stream_api;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import common.Order;
import common.OrderItem;
import common.OrderStatus;

public class OrderAnalyticsImperativeService {
	// ===== 1. getLeadingActiveStreak =====
	// Tương đương takeWhile: duyệt tuần tự, gặp điều kiện sai là DỪNG HẲN (break),
	// không xét tiếp phần tử phía sau, dù phía sau có thỏa điều kiện hay không.
	public List<Order> getLeadingActiveStreak(List<Order> orders) {
		List<Order> result = new ArrayList<>();
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.CANCELLED) {
				break; // <-- đây chính là "takeWhile dừng lại", khác hẳn continue của filter
			}
			result.add(order);
		}
		return result;
	}

	// ===== 2. getOrdersFromFirstBigSpender =====
	// Tương đương dropWhile: có 1 biến cờ (flag) đánh dấu "đã qua điểm bỏ hay
	// chưa".
	// Trước khi flag bật, cứ gặp phần tử thỏa "còn thấp hơn threshold" thì bỏ qua
	// (không add).
	// Khi flag bật rồi thì KHÔNG kiểm tra threshold nữa, add tất cả phần tử còn lại
	// vô điều kiện.
	public List<Order> getOrdersFromFirstBigSpender(List<Order> orders, double threshold) {
		List<Order> result = new ArrayList<>();
		boolean pastDropPoint = false; // cờ: đã qua điểm "bắt đầu giữ" hay chưa
		for (Order order : orders) {
			if (!pastDropPoint) {
				double total = calculateOrderTotal(order);
				if (total < threshold) {
					continue; // vẫn đang trong giai đoạn "bỏ", nhảy qua phần tử này
				}
				pastDropPoint = true; // gặp điểm đạt threshold đầu tiên -> bật cờ, không tắt lại nữa
			}
			result.add(order); // từ đây trở đi add vô điều kiện, không check threshold nữa
		}
		return result;
	}

	// ===== 3. getAllItemsFlattened =====
	// Tương đương flatMap: vòng lặp LỒNG NHAU 2 tầng — lặp Order, rồi lặp tiếp
	// OrderItem
	// bên trong từng Order, gộp chung vào 1 list phẳng duy nhất.
	public List<OrderItem> getAllItemsFlattened(List<Order> orders) {
		List<OrderItem> result = new ArrayList<>();
		for (Order order : orders) { // tầng ngoài: từng Order
			for (OrderItem item : order.getItems()) { // tầng trong: từng Item của Order đó
				result.add(item); // "nở" ra và gộp thẳng vào 1 list chung
			}
		}
		return result;
	}

	// ===== 4. getRevenueByCategory =====
	// Tương đương groupingBy + summingDouble: dùng HashMap làm "container mutable"
	// để cộng dồn.
	// Đây chính là bản chất "mutable reduction" mà sách nói collect() làm — bạn
	// đang thấy nó trần trụi.
	public Map<String, Double> getRevenueByCategory(List<Order> orders) {
		Map<String, Double> result = new HashMap<>();
		for (Order order : orders) {
			for (OrderItem item : order.getItems()) {
				String category = item.getCategory();
				double amount = item.getQuantity() * item.getUnitPrice();
				// getOrDefault: nếu category chưa có trong map, coi như đang có sẵn 0.0 để cộng
				// vào
				double currentTotal = result.getOrDefault(category, 0.0);
				result.put(category, currentTotal + amount);
			}
		}
		return result;
	}

	// ===== 5. countOrdersByStatus =====
	// Tương đương groupingBy + counting(): cũng dùng HashMap, nhưng cộng dồn số đếm
	// (Long) thay vì tiền.
	public Map<OrderStatus, Long> countOrdersByStatus(List<Order> orders) {
		Map<OrderStatus, Long> result = new HashMap<>();
		for (Order order : orders) {
			OrderStatus status = order.getStatus();
			long currentCount = result.getOrDefault(status, 0L);
			result.put(status, currentCount + 1);
		}
		return result;
	}

	// ===== 6. hasAnyOrderExceeding =====
	// Tương đương anyMatch: đây LÀ nơi bạn thấy rõ nhất "short-circuiting" bằng mắt
	// thường —
	// return true NGAY LẬP TỨC khi gặp phần tử đầu tiên thỏa điều kiện, không cần
	// xét hết list.
	public boolean hasAnyOrderExceeding(List<Order> orders, double limit) {
		for (Order order : orders) {
			if (calculateOrderTotal(order) > limit) {
				return true; // <-- short-circuit: thoát ngay, không cần duyệt tiếp
			}
		}
		return false; // duyệt hết mà không gặp phần tử nào thỏa -> false
	}

	// ===== 7. areAllOrdersProcessed =====
	// Tương đương allMatch: short-circuit theo chiều NGƯỢC LẠI — return false ngay
	// khi gặp
	// phần tử ĐẦU TIÊN không thỏa (không cần đợi xét hết).
	public boolean areAllOrdersProcessed(List<Order> orders) {
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.PENDING) {
				return false; // gặp 1 cái không thỏa là đủ để biết "không phải tất cả" rồi
			}
		}
		return true; // duyệt hết không gặp phản ví dụ nào -> true
	}

	// ===== 8. hasNoCancelledOrders =====
	// Tương đương noneMatch: giống allMatch nhưng đảo điều kiện kiểm tra.
	public boolean hasNoCancelledOrders(List<Order> orders) {
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.CANCELLED) {
				return false; // gặp 1 cái CANCELLED là đủ để biết "có" tồn tại -> noneMatch = false
			}
		}
		return true;
	}

	// ===== 9. findFirstPendingOrder =====
	// Tương đương filter + findFirst: PHẢI duyệt đúng thứ tự, return ngay phần tử
	// đầu tiên thỏa.
	public Optional<Order> findFirstPendingOrder(List<Order> orders) {
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.PENDING) {
				return Optional.of(order); // gặp cái đầu tiên là return luôn, không cần biết còn cái nào khác
			}
		}
		return Optional.empty(); // duyệt hết mà không có -> Optional rỗng, KHÔNG return null
	}

	// ===== 10. findAnyCancelledOrder =====
	// Tương đương filter + findAny: về mặt code sequential, viết y hệt findFirst
	// (vì vòng lặp
	// for tuần tự vốn dĩ không có khái niệm "tự do chọn phần tử" như khi chạy song
	// song).
	// Đây chính là điều thú vị: sự khác biệt findFirst/findAny chỉ THỰC SỰ có ý
	// nghĩa khi Stream
	// chạy parallel (chia dữ liệu ra nhiều luồng) — với vòng lặp for tuần tự như
	// thế này thì 2 khái
	// niệm đó không phân biệt được, vì làm gì có "nhiều luồng" để tự do chọn.
	public Optional<Order> findAnyCancelledOrder(List<Order> orders) {
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.CANCELLED) {
				return Optional.of(order);
			}
		}
		return Optional.empty();
	}

	// ===== 11. calculateTotalRevenue =====
	// Tương đương reduce CÓ identity: biến "accumulator" (acc) chính là identity
	// ban đầu (0.0),
	// mỗi vòng lặp CẬP NHẬT accumulator bằng giá trị mới — đây là điểm khác reduce
	// KHÔNG identity
	// (câu 12) là accumulator luôn có sẵn giá trị khởi đầu hợp lệ, dù list rỗng vẫn
	// trả về được 0.0.
	public double calculateTotalRevenue(List<Order> orders) {
		double acc = 0.0; // <-- chính là "identity" trong reduce(identity, accumulator)
		for (Order order : orders) {
			if (order.getStatus() == OrderStatus.CANCELLED) {
				continue; // bỏ qua đơn bị hủy, không cộng vào acc
			}
			for (OrderItem item : order.getItems()) {
				acc = acc + (item.getQuantity() * item.getUnitPrice()); // <-- accumulator = accumulator(acc, item)
			}
		}
		return acc;
	}

	// ===== 12. findHighestValueOrder =====
	// Tương đương reduce KHÔNG identity (hoặc max()): KHÔNG có giá trị khởi đầu "0
	// đơn hàng"
	// hợp lý, nên phải xử lý case rỗng RIÊNG bằng Optional.empty(), không thể gán
	// acc = null rồi
	// so sánh bừa (dễ NPE). Biến "currentBest" chỉ được gán khi gặp phần tử ĐẦU
	// TIÊN của list.
	public Optional<Order> findHighestValueOrder(List<Order> orders) {
		Order currentBest = null;
		for (Order order : orders) {
			if (currentBest == null) {
				currentBest = order; // phần tử đầu tiên -> tạm coi là "tốt nhất" để có cái so sánh
				continue;
			}
			if (calculateOrderTotal(order) > calculateOrderTotal(currentBest)) {
				currentBest = order; // tìm được cái tốt hơn -> thay thế
			}
		}
		// currentBest vẫn có thể là null nếu orders rỗng ngay từ đầu -> bọc Optional ở
		// bước cuối
		return Optional.ofNullable(currentBest);
	}

	// ===== 13. getUnitPriceStatistics =====
	// Tương đương mapToDouble().summaryStatistics(): DoubleSummaryStatistics thật
	// ra bên trong
	// JDK cũng chỉ là 4 biến mutable (count, sum, min, max) được cập nhật dần —
	// viết tay ra đây
	// để thấy rõ bản chất, dùng constructor mặc định rồi gọi accept() từng giá trị
	// 1.
	public DoubleSummaryStatistics getUnitPriceStatistics(List<Order> orders) {
		DoubleSummaryStatistics stats = new DoubleSummaryStatistics(); // count=0, sum=0, min=+Inf, max=-Inf
		for (Order order : orders) {
			for (OrderItem item : order.getItems()) {
				stats.accept(item.getUnitPrice()); // mỗi lần accept: count++, sum+=x, min=Math.min(min,x),
													// max=Math.max(max,x)
			}
		}
		return stats;
	}

	// ===== Helper dùng chung =====
	private double calculateOrderTotal(Order order) {
		double total = 0.0;
		for (OrderItem item : order.getItems()) {
			total += item.getQuantity() * item.getUnitPrice();
		}
		return total;
	}
}
