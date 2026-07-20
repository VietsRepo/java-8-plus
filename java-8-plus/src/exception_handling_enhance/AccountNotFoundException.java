package exception_handling_enhance;

public class AccountNotFoundException extends BusinessException {

	public AccountNotFoundException(String message) {
		super("[ACCOUNT_NOT_FOUND] " + message);
	}

}
