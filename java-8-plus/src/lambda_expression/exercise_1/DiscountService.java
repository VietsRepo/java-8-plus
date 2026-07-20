package lambda_expression.exercise_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.OldOrder;
import common.OrderStatus;

public class DiscountService {

	public record OrderDiscountResult(OldOrder order, double discountAmount, double finalAmount) {
	}

	public List<OrderDiscountResult> applyDiscount(List<OldOrder> orders, Map<OrderStatus, DiscountStrategy> strategyMap) {
		List<OrderDiscountResult> results = new ArrayList<>();
		for (OldOrder order : orders) {
			DiscountStrategy strategy = strategyMap.get(order.getStatus());
			double discount = strategy.calculate(order);
			results.add(new OrderDiscountResult(order, discount, order.getTotalAmount() - discount));
		}
		return results;
	}

}
