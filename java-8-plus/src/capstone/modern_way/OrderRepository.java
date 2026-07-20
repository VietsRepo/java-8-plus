package capstone.modern_way;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
	List<Order> findByCustomerId(String customerId);
}
