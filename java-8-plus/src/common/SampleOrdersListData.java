package common;

import java.time.LocalDate;
import java.util.List;

public class SampleOrdersListData {
	public static final List<OldOrder> oldOrders = List.of(
			new OldOrder(1L, "Boo", 12_500, OrderStatus.SHIPPED),
			new OldOrder(2L, "Bam", 256_230, OrderStatus.CONFIRMED),
			new OldOrder(3L, "Heg", 70_000, OrderStatus.SHIPPED),
			new OldOrder(4L, "Lee", 12_500, OrderStatus.CANCELLED),
			new OldOrder(5L, "Son", 999_123, OrderStatus.DELIVERED),
			new OldOrder(6L, "Boo", 789_000, OrderStatus.PENDING),
			new OldOrder(7L, "Heg", 289_000, OrderStatus.CANCELLED),
			new OldOrder(8L, "Adidas", 800_000, OrderStatus.DELIVERED),
			new OldOrder(9L, "Nike", 289_000, OrderStatus.CONFIRMED),
			new OldOrder(10L, "Nike", 299_000, OrderStatus.PENDING)
		);
	
	public static List<Order> generateSampleOrders() {
		return List.of(
				new Order("ORD-001", "CUST-01", OrderStatus.DELIVERED, LocalDate.of(2025, 1, 5),
						List.of(new OrderItem("Wireless Mouse", "Electronics", 2, 15.0),
								new OrderItem("USB Cable", "Electronics", 3, 5.0))),

				new Order("ORD-002", "CUST-02", OrderStatus.DELIVERED, LocalDate.of(2025, 1, 8),
						List.of(new OrderItem("Office Chair", "Furniture", 1, 120.0))),

				// Đơn CANCELLED sớm -> dùng để test getLeadingActiveStreak dừng ngay tại đây
				new Order("ORD-003", "CUST-03", OrderStatus.CANCELLED, LocalDate.of(2025, 1, 10),
						List.of(new OrderItem("Bluetooth Speaker", "Electronics", 1, 45.0))),

				new Order("ORD-004", "CUST-04", OrderStatus.SHIPPED, LocalDate.of(2025, 1, 12),
						List.of(new OrderItem("Desk Lamp", "Furniture", 2, 25.0),
								new OrderItem("Notebook", "Stationery", 5, 2.0))),

				// Đơn giá trị lớn -> test getOrdersFromFirstBigSpender (tổng = 500.0)
				new Order("ORD-005", "CUST-05", OrderStatus.CONFIRMED, LocalDate.of(2025, 1, 15),
						List.of(new OrderItem("Laptop Stand", "Electronics", 2, 200.0),
								new OrderItem("Keyboard", "Electronics", 1, 100.0))),

				// PENDING đầu tiên theo thời gian -> test findFirstPendingOrder
				new Order("ORD-006", "CUST-06", OrderStatus.PENDING, LocalDate.of(2025, 1, 18),
						List.of(new OrderItem("Monitor", "Electronics", 1, 180.0))),

				new Order("ORD-007", "CUST-07", OrderStatus.DELIVERED, LocalDate.of(2025, 1, 20),
						List.of(new OrderItem("Bookshelf", "Furniture", 1, 90.0),
								new OrderItem("Pen Set", "Stationery", 4, 3.5))),

				// Giá trị thấp hơn threshold nhưng nằm SAU big spender -> phải vẫn nằm trong
				// kết quả câu 2
				new Order("ORD-008", "CUST-08", OrderStatus.SHIPPED, LocalDate.of(2025, 1, 22),
						List.of(new OrderItem("Sticky Notes", "Stationery", 10, 1.0))),

				new Order("ORD-009", "CUST-09", OrderStatus.PENDING, LocalDate.of(2025, 1, 25),
						List.of(new OrderItem("Webcam", "Electronics", 1, 60.0))),

				// CANCELLED thứ 2, nằm cuối -> loại khỏi calculateTotalRevenue
				new Order("ORD-010", "CUST-10", OrderStatus.CANCELLED, LocalDate.of(2025, 1, 28),
						List.of(new OrderItem("Gaming Chair", "Furniture", 1, 300.0))));
	}
}
