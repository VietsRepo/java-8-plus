package capstone.modern_way;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import capstone.modern_way.Order.OrderStatus;

public class Main {

	public static void main(String[] args) {
		List<Order> order1 = List.of(
			new Order("ORD-001", OrderStatus.COMPLETED, new BigDecimal("150.50")),
			new Order("ORD-002", OrderStatus.CANCELLED, new BigDecimal("45.00"))
		);
		List<Order> order2 = List.of(
			new Order("ORD-003", OrderStatus.COMPLETED, new BigDecimal("299.99")),
			new Order("ORD-004", OrderStatus.COMPLETED, new BigDecimal("15.25"))
		);
		List<Order> order3 = List.of(
			new Order("ORD-005", OrderStatus.CANCELLED, new BigDecimal("89.90"))
		);
		
		Map<String, List<Order>> customerOrders = Map.of(
			"CUST-001", order1,
			"CUST-002", order2,
			"CUST-003", order3
		);
		
		// Case 2: khách hàng không có đơn hàng nào (orders = null từ repository, giả
		// lập DB trả về null)
		OrderReportService service1 = new OrderReportService(
			new FakeOrderRepository(customerOrders),
			new FakeNotificationService(false)
		);
		System.out.println(service1.generateReport("CUST-004", "SUMMARY"));
				
				
		// Case 1+3: type = "SUMMARY", "DETAIL", và 1 type không hợp lệ (ví dụ "PDF")
		System.out.println(service1.generateReport("CUST-001", "SUMMARY"));
		System.out.println(service1.generateReport("CUST-002", "DETAIL"));
		System.out.println(service1.generateReport("CUST-001", "PDF"));

		// Case 4: notificationService.send() ném exception thật (test xem code cũ đang
		// "ignore" âm thầm - đây có phải hành vi đúng không?)
		OrderReportService service2 = new OrderReportService(
			new FakeOrderRepository(customerOrders),
			new FakeNotificationService(true)
		);
		System.out.println(service2.generateReport("CUST-001", "SUMMARY"));

		// Case 5: amount là double - liệu có nên đổi sang BigDecimal? (liên hệ Module
		// 24 mình từng nói)
		// đã đổi :D
	}
}
