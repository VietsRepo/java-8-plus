package optional;

import java.util.Optional;

public class Address {
	private final String city;
	private final Optional<String> postalCode; // 1 số nước không dùng postal code

	public Address(String city, Optional<String> postalCode) {
		this.city = city;
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public Optional<String> getPostalCode() {
		return postalCode;
	}
}
