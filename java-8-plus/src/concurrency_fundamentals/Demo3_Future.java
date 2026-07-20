package concurrency_fundamentals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo3_Future {

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

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		// Tạo "nhóm công nhân" gồm 2 thread, dùng đi dùng lại được nhiều lần
		ExecutorService pool = Executors.newFixedThreadPool(2);

		// submit(): "giao việc" cho pool, trả về NGAY 1 cái "phiếu hẹn" (Future), không
		// đợi
		Future<Integer> y = pool.submit(() -> f(10));
		Future<Integer> z = pool.submit(() -> g(10));

		// .get(): CẦM PHIẾU đi lấy kết quả - đây mới là chỗ ĐỢI
		int result = y.get() + z.get();

		long duration = System.currentTimeMillis() - start;
		System.out.println("Kết quả: " + result);
		System.out.println("Tổng thời gian chạy: " + duration + "ms");

		pool.shutdown(); // dọn dẹp pool, không quên bước này
	}
}
