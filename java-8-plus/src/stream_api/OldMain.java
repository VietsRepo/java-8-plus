package stream_api;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import common.OldOrder;
import common.SampleOrdersListData;
import common.OrderStatus;

public class OldMain {

	public static void main(String[] args) {
		double minTotalAmount = 100_000;
		
		/**
		 * lấy danh sách tên khách hàng (không trùng lặp) có order với totalAmount lớn hơn ngưỡng mặc đinh
		 * sắp xếp theo a-z
		 */
		List<String> customerByOrder = SampleOrdersListData.oldOrders.stream()
			.filter(order -> order.getTotalAmount() > minTotalAmount)
			.map(OldOrder::getCustomerName)
			.distinct()
			.sorted()
			.toList();
		
		customerByOrder.forEach(System.out::println);

		/**
		 * lấy danh sách order ở trang thứ 2, mỗi trang tôi giả sử có 2 phần tử
		 * danh sách này order sắp xếp giảm dần theo totalAmount trước khi phân trang
		 */
		Stream<OldOrder> ordersSorted  = SampleOrdersListData.oldOrders.stream()
			.sorted(Comparator.comparingDouble(OldOrder::getTotalAmount).reversed());
//			.sorted((order1, order2) -> Double.compare(order1.getTotalAmount(), order2.getTotalAmount()));
		
		int pageSize = 2;
		int pageNumber = 2;
		
		List<OldOrder> ordersPage2 = ordersSorted.skip((pageNumber - 1) * pageSize)
			.limit(pageSize)
			.toList();
		
		ordersPage2.forEach(System.out::println);
		
		/**
		 * Chứng minh laziness and element by element
		 */
		double totalOrderByStatusPaid = SampleOrdersListData.oldOrders.stream()
			.filter(order -> {
				System.out.println("element: " + order.getId());
				
				return order.getStatus() == OrderStatus.SHIPPED;
			})
			.mapToDouble(OldOrder::getTotalAmount)
			.sum();
		
		System.out.println("Total: " + totalOrderByStatusPaid);
		
		
		/**
		 * Dùng lại 1 Stream
		 */
		System.out.println(ordersSorted.count());
	}

}
