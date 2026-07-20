package exception_handling_enhance;

import java.math.BigDecimal;

public class TransferTransactionService implements AutoCloseable {
	 
    private static final BigDecimal MAX_TRANSFER_AMOUNT = new BigDecimal("500000000");
 
    // Cờ giả lập lỗi khi close() - dùng để demo Case 5 (suppressed exception).
    // Trong thực tế, close() sẽ fail vì lý do thật (mất kết nối, disk full khi ghi log...),
    // ở đây ta chủ động điều khiển để tái hiện tình huống đó.
    private final boolean simulateAuditLogFailure;
 
    public TransferTransactionService() {
        this(false);
    }
 
    public TransferTransactionService(boolean simulateAuditLogFailure) {
        this.simulateAuditLogFailure = simulateAuditLogFailure;
    }
 
    public void transfer(Account source, Account des, BigDecimal amount) {
        if (des == null) {
            throw new AccountNotFoundException("Recipient account not found!");
        }
        if (amount.signum() <= 0) {
            throw new InvalidAmountException("Your entered amount is invalid!");
        } else if (amount.compareTo(source.balance()) > 0) {
            throw new InsufficientFundsException("Your account has insufficient funds!");
        } else if (amount.compareTo(MAX_TRANSFER_AMOUNT) > 0) {
            throw new TransferLimitExceededException("The transfer exceeds your transaction limit!");
        }
        System.out.printf("Transaction Successful: %s -> %s, Amount: %s%n",
                source.id(), des.id(), amount.toPlainString());
    }
 
    @Override
    public void close() throws AuditLogException {
        if (simulateAuditLogFailure) {
            throw new AuditLogException("Failed to write audit log to storage!");
        }
        System.out.println("Close transaction!");
    }
}
