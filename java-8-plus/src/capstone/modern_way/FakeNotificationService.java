package capstone.modern_way;

public class FakeNotificationService implements NotificationService {
	private final boolean shouldFail;

	FakeNotificationService(boolean shouldFail) {
		this.shouldFail = shouldFail;
	}

	@Override
	public void send(String customerId, String message) throws Exception {
		if (shouldFail)
			throw new RuntimeException("Notification server down");
		System.out.println("Notified " + customerId);
	}
}