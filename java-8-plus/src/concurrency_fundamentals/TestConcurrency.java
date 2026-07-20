package concurrency_fundamentals;

public class TestConcurrency {

	// Giả lập 2 "việc nặng" độc lập - ví dụ gọi 2 API khác nhau - mỗi cái tốn 2.5
	// giây
	static void task1() {
		System.out.println("Task 1 bắt đầu...");
		sleep(2500);
		System.out.println("Task 1 xong.");
	}

	static void task2() {
		System.out.println("Task 2 bắt đầu...");
		sleep(2500);
		System.out.println("Task 2 xong.");
	}

	static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		// ========== TRƯỜNG HỢP 1: KHÔNG dùng Thread (tuần tự) ==========
		System.out.println("=== BẮT ĐẦU CHẠY TUẦN TỰ ===");
		long start1 = System.currentTimeMillis();

		task1();
		task2();

		long duration1 = System.currentTimeMillis() - start1;
		System.out.println("=== TUẦN TỰ xong sau: " + duration1 + "ms (~" + (duration1 / 1000.0) + "s) ===");

		System.out.println();

		// ========== TRƯỜNG HỢP 2: CÓ dùng Thread (song song) ==========
		System.out.println("=== BẮT ĐẦU CHẠY SONG SONG ===");
		long start2 = System.currentTimeMillis();

		Thread t1 = new Thread(TestConcurrency::task1);
		Thread t2 = new Thread(TestConcurrency::task2);

		t1.start();
		t2.start();

		t1.join(); // BẮT BUỘC phải có, nếu không main sẽ không đợi t1/t2 chạy xong
		t2.join(); // trước khi đo thời gian và in kết quả

		long duration2 = System.currentTimeMillis() - start2;
		System.out.println("=== SONG SONG xong sau: " + duration2 + "ms (~" + (duration2 / 1000.0) + "s) ===");
	}
}
