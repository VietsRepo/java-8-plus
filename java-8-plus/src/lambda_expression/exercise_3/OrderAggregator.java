package lambda_expression.exercise_3;

import java.util.Map;

import common.OldOrder;

@FunctionalInterface
public interface OrderAggregator {

	void aggregate(OldOrder order, Map<String, Double> revenueByCustomer);
}
