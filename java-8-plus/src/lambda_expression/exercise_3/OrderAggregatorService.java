package lambda_expression.exercise_3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.OldOrder;

public class OrderAggregatorService {

	public Map<String, Double> runAggregation(List<OldOrder> orders, OrderAggregator aggregator) {
		Map<String, Double> customerOrders = new HashMap<>();
		for (OldOrder order : orders) {
			aggregator.aggregate(order, customerOrders);
		}

		return customerOrders;
	}
}
