package lambda_expression.exercise_1;

import java.util.List;
import java.util.Map;

import common.SampleOrdersListData;
import common.OrderStatus;
import lambda_expression.exercise_1.DiscountService.OrderDiscountResult;

public class Main {
	
	public static void main(String[] args) {
		double maxDiscount = 0.15;
		Map<OrderStatus, DiscountStrategy> strategyMap = Map.of(
				OrderStatus.PENDING, order -> order.getTotalAmount() * 0.05,
				OrderStatus.SHIPPED, order -> order.getTotalAmount() * 0.1,
				OrderStatus.DELIVERED, order -> order.getTotalAmount() * maxDiscount,
				OrderStatus.CANCELLED, order -> 0.0
		);
		
		DiscountService service = new DiscountService();
		List<OrderDiscountResult> results = service.applyDiscount(SampleOrdersListData.oldOrders, strategyMap);
		
		results.forEach(
			r -> System.out.printf("Order #%d (%s): giá gốc=%.0f, giảm=%.0f, còn lại=%.0f%n",
			r.order().getId(), r.order().getStatus(), r.order().getTotalAmount(), r.discountAmount(), r.finalAmount())
		);
	}

}
