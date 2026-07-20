package capstone;

public interface NotificationService {
	void send(String customerId, String message) throws Exception;
}
