package playground.spring.java_lab;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		getSomethingByOptional()
			.ifPresent(s -> System.out.println(s.toUpperCase()));
	}

	private static Optional<String> getSomethingByOptional() {
		return Optional.of("private static Optional<String>");
	}
}
