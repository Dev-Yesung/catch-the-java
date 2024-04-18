package playground.spring.java_lab;

// https://woovictory.github.io/2020/07/05/Java-binding/
// https://www.geeksforgeeks.org/static-vs-dynamic-binding-in-java/
public class BindingTest {

	public static void main(String[] args) {
		SuperClass superClass = new SuperClass();
		superClass.methodA();
		superClass.methodB();

		SuperClass subClass = new SubClass();
		subClass.methodA();
		subClass.methodB();
	}

	private static class SuperClass {

		void methodA() {
			System.out.println("SuperClass A");
		}

		public static void methodB() {
			System.out.println("SuperClass B");
		}

	}

	private static class SubClass extends SuperClass {

		@Override
		void methodA() {
			System.out.println("SubClass A");
		}

		public static void methodB() {
			System.out.println("SubClass B");
		}

	}

}
