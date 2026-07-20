package optional;

import java.util.Optional;

public class Customer {
	private final String customerId;
	private final String name;
	private final Optional<String> email; // không phải khách nào cũng cung cấp email
	private final Optional<Address> address; // có thể chưa cập nhật địa chỉ

	public Customer(String customerId, String name, Optional<String> email, Optional<Address> address) {
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.address = address;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public Optional<String> getEmail() {
		return email;
	}

	public Optional<Address> getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", address=" + address
				+ "]";
	}
}
