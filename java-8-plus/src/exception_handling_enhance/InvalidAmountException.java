package exception_handling_enhance;

public class InvalidAmountException extends BusinessException {

	public InvalidAmountException(String message) {
		super("[INVALID_AMOUNT] " + message);
	}

}
