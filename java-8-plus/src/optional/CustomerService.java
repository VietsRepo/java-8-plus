package optional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CustomerService {

	public String getEmailOrDefault(Customer customer) {
		return customer.getEmail().orElse("no-email@company.com");
	}
	
	public String getCityDisplay(Customer customer) {
		return customer.getAddress()
				.map(Address::getCity)
				.orElse("Chưa cập nhật");
	}
	
	public String getFullAddressDisplay(Customer customer) {
		return customer.getAddress()
				.map(address -> address.getPostalCode()
					.map(postalCode -> address.getCity() + ", " + postalCode)
					.orElse(address.getCity()))
				.orElse("Chưa cập nhật");
	}
	
	public boolean hasValidEmail(Customer customer) {
		return customer.getEmail()
				.filter(email -> email.contains("@")).isPresent();
	}
	
	public List<String> getAllEmails(List<Customer> customers) {
		// not recommend way
//		.map(customer -> customer.getEmail())
//		.filter(Optional::isPresent)
//		.map(Optional::get)
//		.toList();
		
		return customers.stream()
				.flatMap(customer -> customer.getEmail().stream())
				.toList();
	}
	
	public Optional<Customer> findCheapestValidCustomer(
		List<Customer> customers, Function<Customer, Optional<Double>> orderTotalLookup
	) {
		return customers.stream()
			.filter(customer -> orderTotalLookup.apply(customer).isPresent())
			.min(Comparator.comparingDouble(
				customer -> orderTotalLookup.apply(customer).get()
			));
	}
}
