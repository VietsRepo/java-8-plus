package optional;

import java.util.List;
import java.util.Optional;

public class SampleData {

	public static List<Customer> generateSampleCustomers() {
		return List.of(
				// Đủ mọi thứ: email, address, postalCode -> test getFullAddressDisplay đầy đủ
				new Customer("CUST-01", "An Nguyen", Optional.of("an.nguyen@gmail.com"),
						Optional.of(new Address("Ho Chi Minh City", Optional.of("700000")))),

				// Có address nhưng KHÔNG có postalCode -> test getFullAddressDisplay chỉ có
				// city
				new Customer("CUST-02", "Binh Tran", Optional.of("binh.tran@yahoo.com"),
						Optional.of(new Address("Ha Noi", Optional.empty()))),

				// KHÔNG có address luôn -> test getCityDisplay/getFullAddressDisplay trả "Chưa
				// cập nhật"
				new Customer("CUST-03", "Chi Le", Optional.of("chi.le@outlook.com"), Optional.empty()),

				// KHÔNG có email -> test getEmailOrDefault trả về default
				new Customer("CUST-04", "Dung Pham", Optional.empty(),
						Optional.of(new Address("Da Nang", Optional.of("550000")))),

				// Có email nhưng SAI định dạng (không có "@") -> test hasValidEmail = false
				new Customer("CUST-05", "Em Vo", Optional.of("khong-hop-le-thieu-at"),
						Optional.of(new Address("Can Tho", Optional.empty()))),

				// Không có gì cả (cả email lẫn address đều rỗng) -> case cực đoan nhất
				new Customer("CUST-06", "Giang Hoang", Optional.empty(), Optional.empty()));
	}

	// Hàm tra cứu tổng đơn hàng giả lập cho câu 6 -> 1 số khách CHƯA TỪNG đặt đơn
	// (Optional rỗng)
	public static Optional<Double> lookupOrderTotal(Customer customer) {
		return switch (customer.getCustomerId()) {
		case "CUST-01" -> Optional.empty();
		case "CUST-02" -> Optional.empty(); // <- thấp nhất trong số CÓ dữ liệu
		case "CUST-03" -> Optional.empty(); // chưa từng đặt đơn -> phải bị loại khỏi kết quả
		case "CUST-04" -> Optional.empty();
		case "CUST-05" -> Optional.empty(); // cũng chưa từng đặt đơn
		case "CUST-06" -> Optional.empty(); // thấp hơn cả CUST-02, nhưng CUST-06 rỗng cả email/address
		default -> Optional.empty();
		};
	}
	
//	public static Optional<Double> lookupOrderTotal(Customer customer) {
//		return switch (customer.getCustomerId()) {
//		case "CUST-01" -> Optional.of(1500.0);
//		case "CUST-02" -> Optional.of(300.0); // <- thấp nhất trong số CÓ dữ liệu
//		case "CUST-03" -> Optional.empty(); // chưa từng đặt đơn -> phải bị loại khỏi kết quả
//		case "CUST-04" -> Optional.of(4200.0);
//		case "CUST-05" -> Optional.empty(); // cũng chưa từng đặt đơn
//		case "CUST-06" -> Optional.of(50.0); // thấp hơn cả CUST-02, nhưng CUST-06 rỗng cả email/address
//		default -> Optional.empty();
//		};
//	}
}
