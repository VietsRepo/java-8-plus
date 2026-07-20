package method_reference;

import common.OldOrder;
import common.OrderStatus;

public record OrderSummaryDto(Long orderId, double totalAmount, OrderStatus status) {

    // Record cho phép định nghĩa thêm constructor tùy chỉnh (gọi là "compact constructor" 
    // hoặc "additional constructor" - sẽ học kỹ ở Module 17 Records)
    public OrderSummaryDto(OldOrder order) {
        this(order.getId(), order.getTotalAmount(), order.getStatus());
    }
}