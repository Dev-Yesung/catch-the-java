package playground.spring.spring_lab.tobi_book.book1.ch06;

import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) {
		Integer a = 3;
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("주소 : " + a.hashCode() + ", " + localDateTime.hashCode());
		System.out.println(a + ", " + localDateTime.toString());

		test(a, localDateTime);
		System.out.println(a + ", " + localDateTime);
	}

	public static void test(Integer a, LocalDateTime localDateTime) {
		System.out.println("주소 : " + a.hashCode() + ", " + localDateTime.hashCode());
		a = 7;
		System.out.println("주소 : " + a.hashCode() + ", " + localDateTime.hashCode());
		localDateTime = LocalDateTime.MAX;
		System.out.println(a + ", " + localDateTime);
	}

}
