package capstone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import capstone.modern_way.Order.OrderStatus;

public class Main {

	public static void main(String[] args) {
		// Case 1: khách hàng có đơn hàng, mix COMPLETED và CANCELLED
		List<Order> order1 = List.of(
			new Order("ORD-001", OrderStatus.COMPLETED.name(), new BigDecimal("150.50").doubleValue()),
			new Order("ORD-002", OrderStatus.CANCELLED.name(), new BigDecimal("45.00").doubleValue())
		);
		List<Order> order2 = List.of(
			new Order("ORD-003", OrderStatus.COMPLETED.name(), new BigDecimal("299.99").doubleValue()),
			new Order("ORD-004", OrderStatus.COMPLETED.name(), new BigDecimal("15.25").doubleValue())
		);
		List<Order> order3 = List.of(
			new Order("ORD-005", OrderStatus.CANCELLED.name(), new BigDecimal("89.90").doubleValue())
		);
			
		Map<String, List<Order>> customerOrders = Map.of(
			"CUST-001", order1,
			"CUST-002", order2,
			"CUST-003", order3
		);
		
		// Case 2: khách hàng không có đơn hàng nào (orders = null từ repository, giả
		// lập DB trả về null)
		OrderReportService service1 = new capstone.OrderReportService(
			new FakeOrderRepository(customerOrders),
			new FakeNotificationService(false)
		);
		System.out.println(service1.generateReport("CUST-004", "SUMMARY"));
		
		
		// Case 3: type = "SUMMARY", "DETAIL", và 1 type không hợp lệ (ví dụ "PDF")
//		System.out.println(service2.generateReport("CUST-001", "SUMMARY"));
//		System.out.println(service2.generateReport("CUST-002", "DETAIL"));
//		System.out.println(service2.generateReport("CUST-001", "PDF"));

		

		

		// Case 4: notificationService.send() ném exception thật (test xem code cũ đang
		// "ignore" âm thầm - đây có phải hành vi đúng không?)
		OrderReportService service2 = new capstone.OrderReportService(
			new FakeOrderRepository(customerOrders),
			new FakeNotificationService(true)
		);
		System.out.println(service2.generateReport("CUST-001", "SUMMARY"));

		// Case 5: amount là double - liệu có nên đổi sang BigDecimal? (liên hệ Module
		// 24 mình từng nói)
	}
}
