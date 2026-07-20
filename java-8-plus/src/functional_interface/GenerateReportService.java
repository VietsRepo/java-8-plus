package functional_interface;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import common.OldOrder;
import common.OrderStatus;

public class GenerateReportService {

	private Predicate<OldOrder> isOrderValid = order -> order.getStatus() != OrderStatus.CANCELLED;
	private Consumer<OldOrder> writeLog = order -> System.out.printf("Order #%s hợp lệ, đóng góp: %f\n", order.getId(),
			order.getTotalAmount());

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
		System.out.println("Tổng doanh thu hợp lệ: " + revenue.orElseGet(() -> calculateFallbackRevenue()));
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
