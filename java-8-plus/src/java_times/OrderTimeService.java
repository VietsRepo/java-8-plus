package java_times;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OrderTimeService {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public Duration getProcessingTime(OrderTimeline order) {
		if (order.getConfirmedAt() == null) {
			return Duration.ZERO;
		}

		return Duration.between(order.getPlacedAt(), order.getConfirmedAt());
	}
	
	public boolean isDeliveryOverdue(OrderTimeline order, LocalDate today) {
		return today.isAfter(order.getExpectedDeliveryDate());
	}
	
	public LocalDate getNextBusinessDayAfterDelivery(OrderTimeline order) {
		LocalDate expectedDeliveryDate = order.getExpectedDeliveryDate();
		DayOfWeek dayOfWeek = expectedDeliveryDate.getDayOfWeek();
		
		int nextBusinessDay = switch (dayOfWeek) {
			case DayOfWeek.FRIDAY -> 3;
			case DayOfWeek.SATURDAY -> 2;
			default -> 1;
		};
		
		return expectedDeliveryDate.plusDays(nextBusinessDay);
	}
	
	public String formatPlacedAtForCustomer(OrderTimeline order) {
		ZonedDateTime placeAtWithZone = order.getPlacedAt().atZone(ZoneId.of("UTC"));
		DateTimeFormatter withZone = formatter.withZone(order.getCustomerZone());

		return withZone.format(placeAtWithZone);
	}
	
	public Period getTimeUntilDelivery(OrderTimeline order, LocalDate today) {
		return Period.between(today, order.getExpectedDeliveryDate());
	}
	
	public List<OrderTimeline> sortByProcessingTimeDesc(List<OrderTimeline> orders) {
		return orders.stream()
				// An easy way to understand
				// (order1, order2) -> getProcessingTime(order2).compareTo(getProcessingTime(order1))
				.sorted(Comparator.comparing(this::getProcessingTime).reversed())
				.toList();
	}
	
}
