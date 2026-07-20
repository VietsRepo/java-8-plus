package java_times;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		OrderTimeService service = new OrderTimeService();
		List<OrderTimeline> ordersTimeline = SampleOrderTimelineData.generateSampleTimelines();
		LocalDate today = LocalDate.of(2024, Month.JUNE, 15);
		LocalDate todayOverDue = LocalDate.of(2026, Month.AUGUST, 20);
		
		System.out.println(service.getProcessingTime(ordersTimeline.get(0)));
		System.out.println(service.getProcessingTime(ordersTimeline.get(1)));
		System.out.println(service.isDeliveryOverdue(ordersTimeline.get(0), today));
		System.out.println(service.isDeliveryOverdue(ordersTimeline.get(0), todayOverDue));
		System.out.println(service.getNextBusinessDayAfterDelivery(ordersTimeline.get(2)));
		System.out.println(service.formatPlacedAtForCustomer(ordersTimeline.get(3)));
		System.out.println(service.getTimeUntilDelivery(ordersTimeline.get(1), today));
		service.sortByProcessingTimeDesc(ordersTimeline).forEach(System.out::println);
	}
}
