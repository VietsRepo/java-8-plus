package concurrency_fundamentals;

public class Demo1_Sequential {

	// Giả lập 2 việc "chậm" - ví dụ gọi API, query DB - mỗi cái tốn 1 giây
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

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		int y = f(10); // đợi 1 giây
		int z = g(10); // đợi thêm 1 giây nữa

		long duration = System.currentTimeMillis() - start;
		System.out.println("Kết quả: " + (y + z));
		System.out.println("Tổng thời gian chạy: " + duration + "ms");
	}
}
