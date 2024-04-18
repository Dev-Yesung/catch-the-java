package playground.spring.java_lab;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest {

	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>();
		set.add(4);
		set.add(3);
		set.add(1);
		set.add(2);
		System.out.println(set);

		set.add(17);

		System.out.println(set);
	}
}
