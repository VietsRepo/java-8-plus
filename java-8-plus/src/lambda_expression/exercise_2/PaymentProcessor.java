package lambda_expression.exercise_2;

import common.OldOrder;

@FunctionalInterface
public interface PaymentProcessor {

	void process(OldOrder order) throws PaymentException;
}
