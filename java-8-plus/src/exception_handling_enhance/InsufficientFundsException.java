package exception_handling_enhance;

public class InsufficientFundsException extends BusinessException {

	public InsufficientFundsException(String message) {
		super("[INSUFFICIENT_FUNDS] " + message);
	}

}
