package capstone;

import java.util.ArrayList;
import java.util.List;

public class OrderReportService {

	private OrderRepository orderRepository;
	private NotificationService notificationService;

	public OrderReportService(OrderRepository orderRepository, NotificationService notificationService) {
		this.orderRepository = orderRepository;
		this.notificationService = notificationService;
	}

	public String generateReport(String customerId, String type) {
		List<Order> orders = orderRepository.findByCustomerId(customerId);
		if (orders == null) {
			orders = new ArrayList<Order>();
		}

		double total = 0;
		List<Order> validOrders = new ArrayList<Order>();
		for (int i = 0; i < orders.size(); i++) {
			Order o = orders.get(i);
			if (o.getStatus().equals("COMPLETED")) {
				total = total + o.getAmount();
				validOrders.add(o);
			}
		}

		String result = "";
		if (type.equals("SUMMARY")) {
			result = "Customer: " + customerId + "\n" + "Total orders: " + validOrders.size() + "\n" + "Total amount: "
					+ total;
		} else if (type.equals("DETAIL")) {
			StringBuilder sb = new StringBuilder();
			sb.append("Customer: " + customerId + "\n");
			for (Order o : validOrders) {
				sb.append("- " + o.getId() + ": " + o.getAmount() + "\n");
			}
			result = sb.toString();
		} else {
			throw new RuntimeException("Unknown report type: " + type);
		}

		try {
			notificationService.send(customerId, "Report generated");
		} catch (Exception e) {
			// ignore
		}

		return result;
	}
}

class Order {
	private String id;
	private String status;
	private double amount;

	public Order(String id, String status, double amount) {
		super();
		this.id = id;
		this.status = status;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public double getAmount() {
		return amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
