package exception_handling_enhance;

import java.math.BigDecimal;

public class ExceptionHandlingMain {

	// Case 1: transfer bình thường, đủ tiền -> thành công
	static Account acc1 = new Account("A001", new BigDecimal("5000000"));
	static Account acc2 = new Account("A002", new BigDecimal("1000000"));

	// Case 3: tài khoản đích null / không tồn tại
	static Account nullAcc = null;

	// Case 6: tài khoản rất giàu, transfer vượt MAX_TRANSFER_AMOUNT nhưng vẫn đủ số
	// dư
	static Account richAcc = new Account("A999", new BigDecimal("600000000"));

	public static void main(String[] args) {
		System.out.println("--- Case 1: transfer hợp lệ ---");
		transfer(acc1, acc2, "2000000", false);

		System.out.println("\n--- Case 2: số dư không đủ ---");
		transfer(acc1, acc2, "999000000", false);

		System.out.println("\n--- Case 3: tài khoản đích null ---");
		transfer(acc1, nullAcc, "100000", false);

		System.out.println("\n--- Case 4: số tiền âm ---");
		transfer(acc1, acc2, "-500000", false);

		System.out.println("\n--- Case 5: lỗi nghiệp vụ trong try body + close() cũng lỗi (suppressed) ---");
		transfer(acc1, acc2, "999000000", true); // insufficient funds + audit log fail cùng lúc

		System.out.println("\n--- Case 6: vượt hạn mức transfer (đủ tiền nhưng quá MAX_TRANSFER_AMOUNT) ---");
		transfer(richAcc, acc2, "550000000", false);

		System.out.println("\n--- Case 7: lỗi hệ thống thật (source == null -> NPE, không phải lỗi nghiệp vụ) ---");
		transfer(null, acc2, "100000", false);
	}

	private static void transfer(Account from, Account to, String amountStr, boolean simulateAuditLogFailure) {
		BigDecimal amount = new BigDecimal(amountStr);
		try (var transferService = new TransferTransactionService(simulateAuditLogFailure)) {
			transferService.transfer(from, to, amount);
		} catch (
			AccountNotFoundException | InvalidAmountException | InsufficientFundsException
			| TransferLimitExceededException e
		) {
			System.out.println("Transfer failed!: " + e.getMessage());
			for (Throwable suppressed : e.getSuppressed()) {
				System.out.println(
						"  -> Suppressed: " + suppressed.getClass().getSimpleName() + " - " + suppressed.getMessage());
			}
		} catch (Exception e) {
			System.out.println("System failed!: " + e.getMessage());
		}
	}
}
