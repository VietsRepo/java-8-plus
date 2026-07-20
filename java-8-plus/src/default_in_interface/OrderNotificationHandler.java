package default_in_interface;

import java.time.LocalDateTime;

public class OrderNotificationHandler implements Loggable, Notifiable, Auditable {
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public OrderNotificationHandler(LocalDateTime updatedAt) {
		this.createdAt = LocalDateTime.of(2026, 7, 1, 9, 5);
		this.updatedAt = updatedAt;
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String getRecipientEmail() {
		return "mepresent@gmail.com";
	}

	@Override
	public String getLogPrefix() {
		return "MASTER MYSELF";
	}

	public void processOrder() {
		logInfo("On my way");
		sendNotification("Focus on my way");
		
		if (isStale(60)) {
			System.out.println("Needed updating!");
		}
	}
}
