package common;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
	private final String orderId;
	private final String customerId;
	private final OrderStatus status;
	private final LocalDate orderDate;
	private final List<OrderItem> items;

	public Order(String orderId, String customerId, OrderStatus status, LocalDate orderDate, List<OrderItem> items) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.status = status;
		this.orderDate = orderDate;
		this.items = items;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public List<OrderItem> getItems() {
		return items;
	}
	
	@Override
	public String toString() {
		String itemsSummary = items.stream()
				.map(item -> 
						"%s x %d (%s) = %.2f".formatted(
							item.getProductName(),
							item.getQuantity(),
							item.getCategory(),
							item.getQuantity() * item.getUnitPrice()))
				.collect(Collectors.joining(", "));

		return "Order[id=%s, customer=%s, status=%s, date=%s, items=[%s]]".formatted(orderId, customerId, status,
				orderDate, itemsSummary);
	}
}
