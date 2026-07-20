package default_in_interface;

public interface Notifiable {
	
	String getRecipientEmail();
	
	default void sendNotification(String content) {
		System.out.printf("Sending to <%s>: <%s>%n", getRecipientEmail(), content);
	}
}
