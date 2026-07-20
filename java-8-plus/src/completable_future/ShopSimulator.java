package completable_future;

public class ShopSimulator {
	private final String shopName;

	public ShopSimulator(String shopName) {
		this.shopName = shopName;
	}

	public double getPrice(String product) {
		delay(800); // giả lập network
		return (product.hashCode() % 500 + shopName.hashCode() % 100) / 10.0;
	}

	public double getExchangeRate() {
		delay(500);
		return 26259.0; // giả lập tỷ giá USD -> VND
	}

	private void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}