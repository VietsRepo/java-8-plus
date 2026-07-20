package java_times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class OrderTimeline {
	private final String orderId;
	private final LocalDateTime placedAt;
	private final LocalDateTime confirmedAt; // có thể null nếu chưa confirm
	private final LocalDate expectedDeliveryDate;
	private final ZoneId customerZone;

	public OrderTimeline(String orderId, LocalDateTime placedAt, LocalDateTime confirmedAt,
			LocalDate expectedDeliveryDate, ZoneId customerZone) {
		super();
		this.orderId = orderId;
		this.placedAt = placedAt;
		this.confirmedAt = confirmedAt;
		this.expectedDeliveryDate = expectedDeliveryDate;
		this.customerZone = customerZone;
	}

	public String getOrderId() {
		return orderId;
	}

	public LocalDateTime getPlacedAt() {
		return placedAt;
	}

	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public LocalDate getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public ZoneId getCustomerZone() {
		return customerZone;
	}

	@Override
	public String toString() {
		return "OrderTimeline [orderId=" + orderId + ", placedAt=" + placedAt + ", confirmedAt=" + confirmedAt
				+ ", expectedDeliveryDate=" + expectedDeliveryDate + ", customerZone=" + customerZone + "]";
	}

}
