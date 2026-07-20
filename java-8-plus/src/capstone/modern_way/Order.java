package capstone.modern_way;

import java.math.BigDecimal;

public record Order(String id, OrderStatus status, BigDecimal amount) {
	
	public enum OrderStatus {
		COMPLETED, CANCELLED
	}
}