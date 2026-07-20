package common;

public class OrderItem {
	
	private final String productName;
	private final String category;
	private final int quantity;
	private final double unitPrice;

	public OrderItem(String productName, String category, int quantity, double unitPrice) {
		this.productName = productName;
		this.category = category;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public String getProductName() {
		return productName;
	}

	public String getCategory() {
		return category;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
	
	@Override
	public String toString() {
		return "OrderItem [productName=" + productName + ", category=" + category + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + "]";
	}
}
