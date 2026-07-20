package java_times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class SampleOrderTimelineData {
	public static List<OrderTimeline> generateSampleTimelines() {
        return List.of(

            // Case bình thường: đã confirm sau 2 giờ, giao đúng hạn (chưa qua)
            new OrderTimeline(
                "ORD-101",
                LocalDateTime.of(2026, 7, 1, 9, 0),
                LocalDateTime.of(2026, 7, 1, 11, 0),
                LocalDate.of(2026, 7, 20),
                ZoneId.of("Asia/Ho_Chi_Minh")
            ),

            // CHƯA confirm (confirmedAt = null) -> test getProcessingTime trả Duration.ZERO
            new OrderTimeline(
                "ORD-102",
                LocalDateTime.of(2026, 7, 5, 14, 30),
                null,
                LocalDate.of(2026, 7, 25),
                ZoneId.of("Asia/Ho_Chi_Minh")
            ),

            // Ngày giao rơi vào THỨ BẢY (2026-07-18 là thứ Bảy) -> test getNextBusinessDayAfterDelivery
            new OrderTimeline(
                "ORD-103",
                LocalDateTime.of(2026, 7, 3, 8, 0),
                LocalDateTime.of(2026, 7, 3, 8, 45),
                LocalDate.of(2026, 7, 18), // Saturday
                ZoneId.of("Asia/Ho_Chi_Minh")
            ),

            // Ngày giao rơi vào CHỦ NHẬT (2026-07-19) -> next business day phải là Thứ Hai 2026-07-20
            new OrderTimeline(
                "ORD-104",
                LocalDateTime.of(2026, 7, 3, 10, 0),
                LocalDateTime.of(2026, 7, 3, 10, 20),
                LocalDate.of(2026, 7, 19), // Sunday
                ZoneId.of("America/New_York")
            ),

            // ĐÃ QUÁ HẠN giao (so với today giả định 2026-07-15) -> test isDeliveryOverdue = true
            new OrderTimeline(
                "ORD-105",
                LocalDateTime.of(2026, 6, 20, 7, 0),
                LocalDateTime.of(2026, 6, 20, 20, 0), // xử lý rất lâu (13 tiếng) -> test sort giảm dần
                LocalDate.of(2026, 7, 10), // đã qua nếu today = 2026-07-15
                ZoneId.of("Europe/Rome")
            ),

            // Xử lý cực nhanh (5 phút) -> test sortByProcessingTimeDesc đứng cuối bảng
            new OrderTimeline(
                "ORD-106",
                LocalDateTime.of(2026, 7, 10, 16, 0),
                LocalDateTime.of(2026, 7, 10, 16, 5),
                LocalDate.of(2026, 7, 30),
                ZoneId.of("Asia/Tokyo")
            )
        );
    }
}
