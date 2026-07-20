package completable_future;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleFunction;

public class PriceFinderService {

	public List<Double> findAllPricesSequential(List<ShopSimulator> shops, String product) {
		long start = System.currentTimeMillis();
		
		List<Double> prices = shops.stream()
			.map(shop -> shop.getPrice(product))
			.toList();
		
		long duration = System.currentTimeMillis() - start;
		System.out.println("findAllPricesSequential (ms): " + duration);
		
		return prices;
	}
	
	public List<Double> findAllPricesConcurrent(List<ShopSimulator> shops, String product) {
		long start = System.currentTimeMillis();
		
		List<CompletableFuture<Double>> priceFuture = shops.stream()
			.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
			.toList();
		
		List<Double> prices = priceFuture.stream()
			.map(CompletableFuture::join)
			.toList();

		long duration = System.currentTimeMillis() - start;
		System.out.println("findAllPricesConcurrent (ms): " + duration);
		
		return prices;
	}
	
	public CompletableFuture<Double> getPriceInVND(ShopSimulator shop, String product) {
		return CompletableFuture.supplyAsync(() -> shop.getPrice(product))
				.thenCombine(CompletableFuture.supplyAsync(shop::getExchangeRate), (price, rate) -> price * rate);
	}
	
	public CompletableFuture<Double> getBestDiscountedPrice(
		ShopSimulator shop,
		String product,
		DoubleFunction<CompletableFuture<Double>> discountService
	) {
		return CompletableFuture.supplyAsync(() -> shop.getPrice(product))
				.thenCompose(discountService::apply);
	}
	
	public Double findCheapestPriceWithTimeout(List<ShopSimulator> shops, String product, long timeoutSeconds) {
		long start = System.currentTimeMillis();
		
		List<CompletableFuture<Double>> priceFuture = shops.stream()
				.map(shop ->
					CompletableFuture.supplyAsync(() -> shop.getPrice(product))
					.orTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.exceptionally(ex -> Double.MAX_VALUE)
				)
				.toList();
		
		Double cheapestPrice = priceFuture.stream()
			.map(CompletableFuture::join)
			.min(Comparator.comparingDouble(price -> price)).get();
		
		long duration = System.currentTimeMillis() - start;
		System.out.println("findCheapestPriceWithTimeout (ms): " + duration);
		
		return cheapestPrice;
	}
}
