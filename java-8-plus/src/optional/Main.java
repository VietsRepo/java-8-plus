package optional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Main {
	
	public static void main(String[] args) {
		CustomerService service = new CustomerService();
		List<Customer> customers = SampleData.generateSampleCustomers();
		Function<Customer, Optional<Double>> orderTotalLookup = SampleData::lookupOrderTotal;
		
		System.out.println(service.getFullAddressDisplay(customers.get(0)));
		System.out.println(service.getFullAddressDisplay(customers.get(1)));
		System.out.println(service.getFullAddressDisplay(customers.get(2)));
		System.out.println(service.hasValidEmail(customers.get(4)));
		System.out.println(service.getAllEmails(customers));
		System.out.println(service.findCheapestValidCustomer(customers, orderTotalLookup));
	}
}
