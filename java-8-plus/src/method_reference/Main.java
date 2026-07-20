package method_reference;

import common.SampleOrdersListData;

public class Main {

	public static void main(String[] args) {
		GenerateReportService report = new GenerateReportService();
		
		report.generateReport(SampleOrdersListData.oldOrders);
		System.out.println(report.convertOrderToOrderSummary(SampleOrdersListData.oldOrders));
		report.pitfallMethodReference();
	}
}
