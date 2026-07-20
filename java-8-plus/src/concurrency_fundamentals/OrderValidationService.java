package concurrency_fundamentals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderValidationService {
	
	public static void main(String[] args) {
		long durationSequence = validateOrderSequential("PD-01", "New York", "C-01");
		long durationConcurrency = validateOrderConcurrent("PD-01", "New York", "C-01");
		boolean isOrderValid = isOrderValid("PD-01", "New York", "C-01");
		
		System.out.println("durationSequence: " + durationSequence);
		System.out.println("durationConcurrency: " + durationConcurrency);
		System.out.println("isOrderValid: " + isOrderValid);
	}

	private static long validateOrderSequential(String productId, String city, String customerId) {
		long startTime = System.currentTimeMillis();

		ExternalServiceSimulator.checkInventory(productId);
		ExternalServiceSimulator.calculateShippingFee(city);
		ExternalServiceSimulator.checkCustomerCredit(customerId);
		
		long duration = System.currentTimeMillis() - startTime;
		
		return duration;
	}
	
	private static long validateOrderConcurrent(String productId, String city, String customerId) {
		long startTime = System.currentTimeMillis();
		
		try(ExecutorService executorService = Executors.newFixedThreadPool(3)) {
			Future<Integer> inventory = executorService.submit(() -> ExternalServiceSimulator.checkInventory(productId));
			Future<Double> shippingFee = executorService.submit(() -> ExternalServiceSimulator.calculateShippingFee(city));
			Future<Boolean> customerCredit = executorService.submit(() -> ExternalServiceSimulator.checkCustomerCredit(customerId));
			
			System.out.println(inventory.get() + " - " + shippingFee.get() + " - " + customerCredit.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		long duration = System.currentTimeMillis() - startTime;
		
		return duration;
	}
	
	private static boolean isOrderValid(String productId, String city, String customerId) {
		long startTime = System.currentTimeMillis();
		
		try(ExecutorService executorService = Executors.newFixedThreadPool(3)) {
			Future<Integer> inventory = executorService.submit(() -> ExternalServiceSimulator.checkInventory(productId));
			Future<Double> shippingFee = executorService.submit(() -> ExternalServiceSimulator.calculateShippingFee(city));
			Future<Boolean> customerCredit = executorService.submit(() -> ExternalServiceSimulator.checkCustomerCredit(customerId));
			
			Integer totalInventory = inventory.get();
			shippingFee.get();
			Boolean isCustomerCredit = customerCredit.get();
			
			long duration = System.currentTimeMillis() - startTime;
			System.out.println("isOrderValidDuration: " +  duration);
			
			return totalInventory > 0 && isCustomerCredit;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
