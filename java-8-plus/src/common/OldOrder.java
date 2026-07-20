package common;

public class OldOrder {
	private final Long id;
	private final String customerName;
	private final double totalAmount;
	private final OrderStatus status;

	public OldOrder(Long id, String customerName, double totalAmount, OrderStatus status) {
		this.id = id;
		this.customerName = customerName;
		this.totalAmount = totalAmount;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Order{id=" + id + ", customer=" + customerName + ", amount=" + totalAmount + ", status=" + status + "}";
	}
}