package playground.spring.java_lab;

public class OverloadingAndOverridingTest extends Person {
	public static void main(String[] args) {

	}

	/*  오버로딩의 조건
		1. 한 클래스 내에서 메소드 명이 동일해야 한다.
		2. 매개변수의 개수 또는 타입이 달라야 한다.
		3. 매개변수는 같고 리턴타입이 다른 경우는 오버로딩이 성립되지 않는다.
		(리턴 타입은 오버로딩을 구현하는데 아무런 영향을 주지 않는다.)
	 */
	void print(int a) {
		System.out.println(a);
	}

	void print(String a) {
		System.out.println(a);
	}

	void print(int a, String str) {
		if (a < 0) {
			throw new RuntimeException();
		}

		for (int i = 0; i < a; i++) {
			System.out.println(str);
		}
	}

	int print(int a, int b) {
		System.out.println(a + b);

		return a + b;
	}

	/*  1. 부모 클래스의 메소드와 메소드 이름이 동일해야 한다.
		2. 매개변수의 타입, 개수, 순서가 일치해야 한다.
		3. 리턴 타입이 동일해야 한다.
		4. 접근 제한자는 부모 클래스의 메소드와 같거나 더 넓은 범위어야 한다.
		5. 부모 메소드의 예외와 같거나 예외의 개수를 줄일 수 있다.
	 */
	@Override
	public void getSomething() {

	}

	@Override
	public void throwException() {
		throw new RuntimeException();
	}

	// 리스코프 치환원칙 위배
	@Override
	public String getName() {
		int a = 4;
		int b = 2;
		System.out.println(a + b);
		super.setNumber(a + b);

		return "Oh Yeah!!";
	}
}
