package stream_api;

import java.util.List;
import java.util.Map;

import common.Order;
import common.OrderItem;
import common.OrderStatus;
import common.SampleOrdersListData;

public class Main {

	public static void main(String[] args) {
		List<Order> orders = SampleOrdersListData.generateSampleOrders();
		OrderAnalyticsService service = new OrderAnalyticsService();
		
		List<Order> ordersleadingActiveStreak = service.getLeadingActiveStreak(orders);
		ordersleadingActiveStreak.forEach(System.out::println);

		System.out.println("=======================================================\n");
		
		List<Order> ordersFromFirstBigSpender = service.getOrdersFromFirstBigSpender(orders, 120.0);
		ordersFromFirstBigSpender.forEach(System.out::println);
		
		System.out.println("=======================================================\n");
		
		List<OrderItem> allItemsFlattened = service.getAllItemsFlattened(orders);
		allItemsFlattened.forEach(System.out::println);
		
		System.out.println("=======================================================\n");
		
		Map<String, Double> revenueByCategory = service.getRevenueByCategory(orders);
		revenueByCategory.forEach((key, value) -> System.out.printf("category: %s - Total Amount: %.1f%n", key, value));
		
		System.out.println("=======================================================\n");
		
		Map<OrderStatus, Long> countOrdersByStatus = service.countOrdersByStatus(orders);
		countOrdersByStatus.forEach((key, value) -> System.out.printf("Status: %s - Total Orders: %d%n", key, value));
		
		System.out.println("=======================================================\n");
		
		boolean hasAnyOrderExceeding = service.hasAnyOrderExceeding(orders, 400.0);
		System.out.println(hasAnyOrderExceeding);
		
		System.out.println("=======================================================\n");
		
		boolean areAllOrdersProcessed = service.areAllOrdersProcessed(orders);
		System.out.println(areAllOrdersProcessed);
		
		System.out.println("=======================================================\n");
		
		boolean hasNoCancelledOrders = service.hasNoCancelledOrders(orders);
		System.out.println(hasNoCancelledOrders);
		
		System.out.println("=======================================================\n");
		
		double totalRevenue = service.calculateTotalRevenue(orders);
		System.out.println(totalRevenue);
	}

}
