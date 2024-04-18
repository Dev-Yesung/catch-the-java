package playground.spring.java_lab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AnonymousAndLambdaTest {

	// 필드 변수에 할당 가능
	private final Comparator<String> anonymousGlobalOrder = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	};

	private final Comparator<String> globalOrder = (str1, str2) -> str1.compareTo(str2);

	public static void main(String[] args) {
		// 변천과정 : 클래스 -> 익명 클래스 -> 람다 + Functional Interface(함수형 인터페이스)
		List<String> list = new ArrayList<>(List.of("public", "static", "void"));

		// by anonymous class
		list.sort(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		System.out.println(list);

		list.sort((str1, str2) -> str2.compareTo(str1));
		System.out.println(list);

		// 익명클래스 할당
		Comparator<String> naturalOrder = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		};

		// 익명클래스를 람다로 변환
		Comparator<String> reverseOrder = (o1, o2) -> o2.compareTo(o1);
	}
}
