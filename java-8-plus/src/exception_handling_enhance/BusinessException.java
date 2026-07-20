package exception_handling_enhance;

public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
}
