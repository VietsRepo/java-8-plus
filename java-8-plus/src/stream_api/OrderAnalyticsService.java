package stream_api;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import common.Order;
import common.OrderItem;
import common.OrderStatus;

public class OrderAnalyticsService {

	private final ToDoubleFunction<OrderItem> calculateTotalAmount = item -> item.getQuantity() * item.getUnitPrice();
	
	public List<Order> getLeadingActiveStreak(List<Order> orders) {
		return orders.stream()
				// nếu dùng filter bình thường thì kết quả sẽ trả về toàn order khác cancelled
				// không thỏa mãn được đề bài, nhưng nếu dùng takeWhile thì nó sẽ dừng lại khi
				// gặp cancelled đầu và lấy phần đầu
				.takeWhile(order -> order.getStatus() != OrderStatus.CANCELLED)
				.toList();
		
	}
	
	public List<Order> getOrdersFromFirstBigSpender(List<Order> orders, double threshold) {
		return orders.stream()
				.dropWhile(order -> calculateOrderTotal(order) < threshold)
				.toList();
	}
	
	public List<OrderItem> getAllItemsFlattened(List<Order> orders) {
		return orders.stream()
				.flatMap(order -> order.getItems().stream()).toList();
	}
	
	public Map<String, Double> getRevenueByCategory(List<Order> orders) {
		return getAllItemsFlattened(orders).stream()
				.collect(
					Collectors.groupingBy(
						OrderItem::getCategory,
						Collectors.summingDouble(calculateTotalAmount)
				));
				
	}
	
	public Map<OrderStatus, Long> countOrdersByStatus(List<Order> orders) {
		return orders.stream()
				.collect(
					Collectors.groupingBy(
						Order::getStatus,
						Collectors.counting()
				));
	}
	
	public boolean hasAnyOrderExceeding(List<Order> orders, double limit) {
		return orders.stream()
				.anyMatch(order -> calculateOrderTotal(order) > limit);
	}
	
	public boolean areAllOrdersProcessed(List<Order> orders) {
		return orders.stream()
				.allMatch(order -> order.getStatus() != OrderStatus.PENDING);
	}
	
	public boolean hasNoCancelledOrders(List<Order> orders) {
		return orders.stream()
				.noneMatch(order -> order.getStatus() == OrderStatus.CANCELLED);
	}
	
	public Optional<Order> findFirstPendingOrder(List<Order> orders) {
		return orders.stream()
				.filter(order -> order.getStatus() == OrderStatus.PENDING)
				.findFirst();
	}
	
	public Optional<Order> findAnyCancelledOrder(List<Order> orders) {
		return orders.stream()
				.filter(order -> order.getStatus() == OrderStatus.CANCELLED)
				// lý do dùng any ở đây vì không quan tâm tới thứ tự của order
				// order cancelled nào cũng như nhau không quan tâm lắm
				// nên chỉ cần lấy ngẫu nhiên order cancelled là được
				// ngoài ra nếu data lớn thì lấy any hiệu năng hơn đặc biệt là parallel
				.findAny();
	}
	
	public double calculateTotalRevenue(List<Order> orders) {
		return getAllItemsFlattened(
				orders.stream()
					.filter(order -> order.getStatus() != OrderStatus.CANCELLED).toList()
				).stream()
				.mapToDouble(calculateTotalAmount)
				.reduce(0.0, (acc, amount) -> acc + amount);
	}
	
	public Optional<Order> findHighestValueOrder(List<Order> orders) {
		return orders.stream()
				.max(Comparator.comparingDouble(this::calculateOrderTotal));
	}
	
	public DoubleSummaryStatistics getUnitPriceStatistics(List<Order> orders) {
		return getAllItemsFlattened(orders).stream()
				.mapToDouble(OrderItem::getUnitPrice)
				.summaryStatistics();
	}
	
	private double calculateOrderTotal(Order order) {
		return order.getItems().stream()
			.collect(Collectors.summingDouble(calculateTotalAmount));
	}
}
