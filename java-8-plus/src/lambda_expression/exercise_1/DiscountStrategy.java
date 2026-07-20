package lambda_expression.exercise_1;

import common.OldOrder;

@FunctionalInterface
public interface DiscountStrategy {
	
	double calculate(OldOrder order);
}
