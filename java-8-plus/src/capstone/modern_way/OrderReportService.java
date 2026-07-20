package capstone.modern_way;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import capstone.modern_way.Order.OrderStatus;

public class OrderReportService {
	
	private final OrderRepository orderRepository;
	private final NotificationService notificationService;

	public OrderReportService(OrderRepository orderRepository, NotificationService notificationService) {
		this.orderRepository = orderRepository;
		this.notificationService = notificationService;
	}

	public String generateReport(String customerId, String type) {
		List<Order> orders = Optional.ofNullable(orderRepository.findByCustomerId(customerId))
			.orElse(List.of());

		List<Order> validOrders = orders.stream()
			.filter(order -> order.status() == OrderStatus.COMPLETED).toList();
		
		BigDecimal total = validOrders.stream()
			.map(Order::amount)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		String result = switch (type) {
			case "SUMMARY" -> """
				Customer: %s
				Total orders: %d
				Total amount: %.2f
				""".formatted(customerId, validOrders.size(), total);
			case "DETAIL" -> {
				String orderDetails = validOrders.stream()
					.map(order -> """
					- %s : %.2f
					""".formatted(order.id(), order.amount())
					)
					.collect(Collectors.joining());
				
				yield """
				Customer: %s
				%s
				""".formatted(customerId, orderDetails);
			}
			default -> throw new RuntimeException("Unknown report type: " + type);
		};

		try {
			notificationService.send(customerId, "Report generated");
		} catch (Exception e) {
			System.out.println("SERVER DOWN: " + e.getMessage());
		}

		return result;
	}

}
