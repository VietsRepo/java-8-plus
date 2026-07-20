package method_reference;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

import common.OldOrder;
import common.OrderStatus;

public class GenerateReportService {

	private final Predicate<OldOrder> isOrderValid = order -> order.getStatus() != OrderStatus.CANCELLED; // giữ lại vì cần check logic status
	private final Consumer<OldOrder> writeLog = order -> System.out.printf("Order #%s hợp lệ, đóng góp: %f%n", order.getId(),
			order.getTotalAmount()); // giữ lại vì cần custom log in ra

	public void generateReport(List<OldOrder> orders) {
		double totalRevenue = 0.0;
		for (OldOrder order : orders) {
			if (isOrderValid.test(order)) {
				writeLog.accept(order);
				totalRevenue += order.getTotalAmount();
			}
		}

		boolean hasValidOrder = orders.stream().anyMatch(isOrderValid);
		Optional<Double> revenue = hasValidOrder ? Optional.of(totalRevenue) : Optional.empty();

		System.out.println("=============================================================");
		// đổi sang method reference vì không có check thêm logic gì cả chỉ đơn gian gọi calculateFallbackRevenue thôi
		System.out.println("Tổng doanh thu hợp lệ: " + revenue.orElseGet(GenerateReportService::calculateFallbackRevenue));
	}
	
	public List<OrderSummaryDto> convertOrderToOrderSummary(List<OldOrder> orders) {
		return orders.stream()
				.map(OrderSummaryDto::new)
				.toList();
	}
	// Đây là code ví dụ cho pitfall
	public void pitfallMethodReference() {
		StringBuilder builder = new StringBuilder("hello");
		IntSupplier lengthOfString = builder::length;
		builder.append("hehe");
		System.out.println(lengthOfString.getAsInt());
	}

	private static double calculateFallbackRevenue() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println("calculateFallbackRevenue() đã chạy xong!");
		return 0.0;
	}
}
