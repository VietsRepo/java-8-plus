package capstone;

import java.util.List;

public interface OrderRepository {
	List<Order> findByCustomerId(String customerId);
}
