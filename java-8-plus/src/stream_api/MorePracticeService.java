package stream_api;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MorePracticeService {
	
	public static void main(String[] args) {
		List<Integer> numbers = List.of(1, 3, 2, 45, 23);
		List<String> words = List.of("helk man", "hi", "mandsf", "hi");
		List<List<Integer>> numbersNested = List.of(
			List.of(1, 2),
			List.of(6, 8),
			List.of(9, 12),
			List.of(1, 0)
		);

		System.out.println(product(numbers));
		System.out.println(shortestWord(words));
		System.out.println(shortestWordWithBesterWay(words));
		System.out.println(countWordsByLength(words));
		System.out.println(partitionEvenOdd(numbers));
		System.out.println(joinNames(words));
		System.out.println(flatten(numbersNested));
		System.out.println(allCharacters(words));
	}

	private static int product(List<Integer> numbers) {
		return numbers.stream()
				.reduce(1, (accumulator, current) -> accumulator * current);
	}
	
	private static Optional<String> shortestWord(List<String> words) {
		return words.stream()
				.reduce((acc, current) -> acc.length() < current.length() ? acc : current);
	}
	
	private static Optional<String> shortestWordWithBesterWay(List<String> words) {
		return words.stream()
				/** 
				 * Đã hiểu vì Comparator.comparingInt(String::length) chỉ là nhận vào thuộc tính muốn so sánh
				 * Và nó trả về lambda (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
				 * Chính là implement của Comparator.compare
				 * */
				.min(Comparator.comparingInt(String::length));
	}
	
	private static Map<Integer, Long> countWordsByLength(List<String> words) {
		return words.stream()
				/**
				 * classifier: "Mỗi phần tử thuộc nhóm nào?" (String::length)
				 * downstream: "Sau khi có một nhóm rồi thì xử lý nhóm đó như thế nào?" (counting())
				 * "Nhóm các String theo length, sau đó với mỗi nhóm hãy đếm xem có bao nhiêu phần tử."
				 */
				.collect(Collectors.groupingBy(String::length, Collectors.counting()));
	}
	
	private static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> numbers) {
		return numbers.stream()
				.collect(Collectors.partitioningBy(number -> number % 2 == 0));
	}
	
	private static String joinNames(List<String> names) {
		return names.stream()
				.collect(Collectors.joining(", ", "[", "]"));
	}
	
	private static List<Integer> flatten(List<List<Integer>> nested) {
		return nested.stream()
				.flatMap(numbers -> numbers.stream())
				.toList();
	}
	
	private static List<Character> allCharacters(List<String> words) {
		return words.stream()
				.flatMap(word -> Arrays.stream(word.split("")))
				.map(charAsString  -> charAsString.charAt(0)).toList();
	}
}
