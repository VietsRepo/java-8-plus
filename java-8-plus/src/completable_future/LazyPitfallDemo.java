package completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LazyPitfallDemo {

	static double slowCall(int id) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return id * 1.5;
	}

	public static void main(String[] args) {
		List<Integer> shopIds = List.of(1, 2, 3, 4, 5);

		// ===== CÁCH SAI: gộp chung 1 pipeline =====
		long start1 = System.currentTimeMillis();
		List<Double> wrongResult = shopIds.stream().map(id -> CompletableFuture.supplyAsync(() -> slowCall(id)))
				.map(CompletableFuture::join) // join() NGAY trong cùng map tiếp theo
				.toList();
		long wrongDuration = System.currentTimeMillis() - start1;
		System.out.println("CÁCH SAI (1 pipeline):   " + wrongDuration + "ms" + " : " + wrongResult);

		// ===== CÁCH ĐÚNG: tách 2 bước =====
		long start2 = System.currentTimeMillis();
		List<CompletableFuture<Double>> futures = shopIds.stream()
				.map(id -> CompletableFuture.supplyAsync(() -> slowCall(id))).toList(); // bước 1: TẠO HẾT 5 future
																						// trước

		List<Double> correctResult = futures.stream().map(CompletableFuture::join) // bước 2: mới bắt đầu join
				.toList();
		long correctDuration = System.currentTimeMillis() - start2;
		System.out.println("CÁCH ĐÚNG (2 bước):      " + correctDuration + "ms");
	}
}
