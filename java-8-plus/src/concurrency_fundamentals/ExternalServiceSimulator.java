package concurrency_fundamentals;

public class ExternalServiceSimulator {

	// Giả lập gọi API bên ngoài chậm (kiểm tra kho hàng)
	public static int checkInventory(String productId) {
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return productId.hashCode() % 100 + 50; // số lượng tồn kho giả lập
	}

	// Giả lập gọi API tính phí ship
	public static double calculateShippingFee(String city) {
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return city.length() * 5.5;
	}

	// Giả lập gọi API kiểm tra credit khách hàng
	public static boolean checkCustomerCredit(String customerId) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return customerId.hashCode() % 10 != 0; // hầu hết pass, 1/10 fail
	}
}
