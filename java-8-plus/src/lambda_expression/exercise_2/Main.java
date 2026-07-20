package lambda_expression.exercise_2;

import common.OldOrder;
import common.SampleOrdersListData;
import lambda_expression.exercise_2.PaymentProcessorService.OrderProcessed;

public class Main {

	public static void main(String[] args) {
		/**
		 * Một method chỉ được phép ném ra checked exception
		 * nếu method đó (hoặc method cha nó override) đã khai báo rõ ràng throws exception đó 
		 * (hoặc lớp cha của exception đó) trong signature.
		 * Lambda implement PaymentProcessor được phép ném PaymentException
		 * đơn giản vì chính PaymentProcessor.process()
		 * đã khai báo throws PaymentException ngay từ đầu
		 */
		PaymentProcessor processPayLimit = order -> processPayLimit(order);
		
		PaymentProcessorService service = new PaymentProcessorService();
		OrderProcessed processBatch = service.processBatch(SampleOrdersListData.oldOrders, processPayLimit);
		
		IO.println("=============================================================");
		IO.println("SUCCESS ORDERS:");
		processBatch.successOrders().forEach(System.out::println);
		IO.println("=============================================================");
		IO.println("FAILED ORDERS:");
		processBatch.failedOrders().forEach(System.out::println);

	}

	private static void processPayLimit(OldOrder order) throws PaymentException {
		if (order.getTotalAmount() > 500_000) {
			throw new PaymentException("Payment processing failed for orderId: " + order.getId());
		}
		
		System.out.printf("Processed order #%s thành công\n", order.getId());
	}

}
