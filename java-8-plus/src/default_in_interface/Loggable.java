package default_in_interface;

public interface Loggable {

	String getLogPrefix();
	
	default void logInfo(String message) {
		System.out.printf("[%s] %s%n", getLogPrefix(), message);
	}
}
