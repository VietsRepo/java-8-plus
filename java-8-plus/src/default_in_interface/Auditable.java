package default_in_interface;

import java.time.Duration;
import java.time.LocalDateTime;

public interface Auditable {
	
	LocalDateTime getCreatedAt();
	
	LocalDateTime getUpdatedAt();
	
	default boolean isStale(int thresholdDays) {
		long days = Duration.between(getUpdatedAt(), LocalDateTime.now()).toDays();

		return days > thresholdDays;
	}

}
