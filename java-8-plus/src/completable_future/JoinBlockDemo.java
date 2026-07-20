package completable_future;

import java.util.concurrent.CompletableFuture;

public class JoinBlockDemo {
	public static void main(String[] args) {
		System.out.println("[main] Bắt đầu, submit task...");

		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("[worker-thread] Bắt đầu chạy task, ngủ 2 giây...");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			System.out.println("[worker-thread] Task xong, trả kết quả.");
			return "Kết quả từ task";
		});

		System.out.println("[main] supplyAsync() ĐÃ RETURN NGAY - dòng này chạy liền, không đợi task");
		System.out.println("[main] Chuẩn bị gọi join() - main SẼ BỊ ĐỨNG lại từ đây...");

		String result = future.join(); // <-- main "đứng hình" ở đây tới khi worker-thread xong

		System.out.println("[main] join() ĐÃ TRẢ VỀ, main được chạy tiếp: " + result);
	}
}