package lambda_expression.exercise_2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import common.OldOrder;
import common.OrderStatus;

public class PaymentProcessorService {
	
	Logger logger = Logger.getLogger(getClass().getName());
	
	public record OrderProccessInfo(long id, double totalAmount, OrderStatus orderStatus) {};
	
	public record OrderProcessed (List<OrderProccessInfo> successOrders, List<OrderProccessInfo> failedOrders) {};

	public OrderProcessed processBatch(List<OldOrder> orders, PaymentProcessor processor) {
		List<OrderProccessInfo> successOrders = new ArrayList<>();
		List<OrderProccessInfo> failedOrders = new ArrayList<>();
		
		for (OldOrder order : orders) {
			try {
				processor.process(order);
				successOrders.add(
					new OrderProccessInfo(order.getId(), order.getTotalAmount(), order.getStatus())
				);
			} catch (PaymentException e) {
				failedOrders.add(
					new OrderProccessInfo(order.getId(), order.getTotalAmount(), order.getStatus())
				);
				logger.info("Payment processing failed for orderId: " + order.getId());
			}
			
		}
		
		return new OrderProcessed(successOrders, failedOrders);
	}

}
