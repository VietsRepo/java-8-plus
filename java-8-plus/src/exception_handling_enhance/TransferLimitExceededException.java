package exception_handling_enhance;

public class TransferLimitExceededException extends BusinessException {

	public TransferLimitExceededException(String message) {
		super("[TRANSFER_LIMIT_EXCEEDED] " + message);
	}

}
