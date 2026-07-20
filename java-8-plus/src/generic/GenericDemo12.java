package generic;

import java.io.Serializable;
import java.util.ArrayList;

public class GenericDemo12 implements Comparable<GenericDemo12> {
	int value;

	GenericDemo12(int value) {
		this.value = value;
	}

	@Override
	public int compareTo(GenericDemo12 other) {
		return Integer.compare(this.value, other.value);
	}
	static <T> T pick(T a1, T a2) { return a2; }
	
	private static void a(Object obj) {}

	public static void main(String[] args) {
		System.out.println("Method thật sự tồn tại trong class (kể cả method 'ẩn' do compiler tự thêm):");
		for (var m : GenericDemo12.class.getDeclaredMethods()) {
			System.out.println("- " + m + "   (bridge method tự sinh? " + m.isBridge() + ")");
		}
		Serializable s = pick("d", new ArrayList<String>());
	}
	

	
}
