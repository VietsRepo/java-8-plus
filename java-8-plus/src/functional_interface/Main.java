package functional_interface;

import common.SampleOrdersListData;

public class Main {

	public static void main(String[] args) {
		GenerateReportService report = new GenerateReportService();
		
		report.generateReport(SampleOrdersListData.oldOrders);
	}
}
