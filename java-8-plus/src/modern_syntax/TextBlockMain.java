package modern_syntax;

import java.util.List;
import java.util.stream.Collectors;

public class TextBlockMain {
	
	public static void main(String[] args) {
		System.out.println(createConfirmationEmail(order1));
		System.out.println("#######################################");
		System.out.println(createConfirmationEmail(order2));
		System.out.println("#######################################");
		System.out.println(createConfirmationEmail(order3));
		System.out.println("#######################################");
		System.out.println(createConfirmationEmail(order4));
	}

	record OrderItem(String name, int quantity, double price) {
	}

	// Case 1: đơn hàng bình thường, nhiều sản phẩm
	static List<OrderItem> order1 = List.of(new OrderItem("Bàn phím cơ", 1, 890000),
			new OrderItem("Chuột không dây", 2, 250000));

	// Case 2: 1 sản phẩm duy nhất - kiểm tra bảng không bị lỗi format khi chỉ có 1
	// dòng
	static List<OrderItem> order2 = List.of(new OrderItem("Màn hình 27 inch", 1, 4500000));

	// Case 3: danh sách rỗng - kiểm tra email vẫn generate hợp lệ, không NPE, không
	// có bảng rỗng kỳ quặc
	static List<OrderItem> order3 = List.of();

	// Case 4: tên sản phẩm có ký tự đặc biệt / dấu ngoặc kép - kiểm tra Text Block
	// xử lý đúng
	static List<OrderItem> order4 = List.of(new OrderItem("Ổ cứng SSD \"Pro\" 1TB", 1, 1990000));

	static String customerName = "Nguyễn Văn A";
	
	private static String createConfirmationEmail(List<OrderItem> orders) {
		String body = orders.stream()
				.map(order -> 
					"""
					Product Name: %s              \s
					Price: %.1f \
					VND
					Quantity: %d
					---------------------------------
					""".formatted(order.name, order.price, order.quantity)
				)
				.collect(Collectors.joining(""));
		
		double total = orders.stream()
				.collect(Collectors.summingDouble(order -> order.price * order.quantity));
		
		String emailContent = """
				=================================
				Customer Name: %s
				=================================
				Order Detail:
				-------------
				%s
				=================================
				Total: %.1f VND
				""".formatted(customerName, body.isBlank() ? "Bạn chưa order sản phẩm nào :)" : body, total);
		
		return emailContent;
	}
}
