package lambda_expression.exercise_3;

import java.util.Map;

import common.OldOrder;
import common.SampleOrdersListData;

public class Main {

	public static void main(String[] args) {
		/**
		 * Điềy này không vi phạm quy tắc effectively final
		 * Vì không gán lại giá trị của biến totalOrdersProcessed
		 * Cụ thể hơn vì đây là array và nó thực chất là object,
		 * khi chúng ta thay đổi giá trị của object thì vẫn không thay đổi reference của nó
		 * 
		 * Còn với totalOrdersProcessed++, bản thân biến int đó bị gán giá trị mới trực tiếp, nên vi phạm effectively final.
		 */
		int[] totalOrdersProcessed = new int[1];
		OrderAggregator aggregate = (order, revenueByCustomer) -> {
			groupOrderByCustomer(order, revenueByCustomer);
			totalOrdersProcessed[0] += 1;
		};

		

		OrderAggregatorService service = new OrderAggregatorService();
		Map<String, Double> aggregatetion = service.runAggregation(SampleOrdersListData.oldOrders, aggregate);
		IO.println(totalOrdersProcessed[0]);
		aggregatetion.forEach((key, value) -> System.out.printf("Customer: %s \nTotalAmount: %.0f\n", key, value));
	}

	private static void groupOrderByCustomer(OldOrder order, Map<String, Double> revenueByCustomer) {
		String customerName = order.getCustomerName();
		revenueByCustomer.merge(customerName, order.getTotalAmount(), Double::sum);
	}

}
