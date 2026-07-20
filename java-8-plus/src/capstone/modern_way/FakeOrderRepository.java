package capstone.modern_way;

import java.util.List;
import java.util.Map;

public class FakeOrderRepository implements OrderRepository {
	private final Map<String, List<Order>> data;

	FakeOrderRepository(Map<String, List<Order>> data) {
		this.data = data;
	}

	@Override
	public List<Order> findByCustomerId(String customerId) {
		return data.get(customerId); // cố tình trả null nếu không có key -> đúng Case 2
	}
}
