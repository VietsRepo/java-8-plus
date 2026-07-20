package default_in_interface;

import java.time.LocalDateTime;

public class Main {
	
	public static void main(String[] args) {
		OrderNotificationHandler handler = new OrderNotificationHandler(
			LocalDateTime.of(2026, 5, 10, 11, 2)
		);
		handler.processOrder();
		
		Z z = new Z();
		z.describe();
	}
}
