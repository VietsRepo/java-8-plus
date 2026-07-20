package completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.DoubleFunction;

public class Main {

	public static void main(String[] args) {
		List<ShopSimulator> shops = List.of(
			new ShopSimulator("TechZone"),
			new ShopSimulator("GadgetWorld"),
			new ShopSimulator("ElectroMart"),
			new ShopSimulator("SmartBuy"),
			new ShopSimulator("QuickTech")
		);
		
		PriceFinderService service = new PriceFinderService();
		DoubleFunction<CompletableFuture<Double>> discountService = price -> CompletableFuture.supplyAsync(() -> price * 0.25);
		
		System.out.println(service.findAllPricesSequential(shops, "thinkpad"));
		System.out.println(service.findAllPricesConcurrent(shops, "thinkpad"));
		System.out.println(service.getPriceInVND(shops.getFirst(), "thinkpad").join());
		System.out.println(service.getBestDiscountedPrice(shops.getFirst(), "thinkpad", discountService).join());
		System.out.println(service.findCheapestPriceWithTimeout(shops, "thinkpad", 1));
	}
}
