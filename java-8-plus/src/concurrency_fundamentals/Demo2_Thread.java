package concurrency_fundamentals;

public class Demo2_Thread {

	static int f(int x) {
		sleep(1000);
		return x * 2;
	}

	static int g(int x) {
		sleep(1000);
		return x * 3;
	}

	static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	// Vì lambda không gán trực tiếp được biến local (effectively final) (Module 2 đã học),
	// dùng mảng 1 phần tử làm "hộp chứa" để lấy kết quả ra khỏi Thread
	static int[] resultY = new int[1];
	static int[] resultZ = new int[1];

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();

		Thread t1 = new Thread(() -> resultY[0] = f(10)); // TẠO thread, CHƯA chạy
		Thread t2 = new Thread(() -> resultZ[0] = g(10));

		t1.start(); // BẮT ĐẦU chạy t1 - không đợi
		t2.start(); // BẮT ĐẦU chạy t2 - chạy CÙNG LÚC với t1

		t1.join(); // ĐỢI t1 chạy xong
		t2.join(); // ĐỢI t2 chạy xong

		long duration = System.currentTimeMillis() - start;
		System.out.println("Kết quả: " + (resultY[0] + resultZ[0]));
		System.out.println("Tổng thời gian chạy: " + duration + "ms");
	}
}
