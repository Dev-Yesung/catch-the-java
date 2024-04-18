package playground.spring.java_lab;

public abstract class Person {
	private static int number = 0;

	protected String getName() {
		return "Person";
	}

	public void setNumber(int number) {
		Person.number = number;
	}

	protected void throwException() {
		throw new IndexOutOfBoundsException();
	}

	abstract void getSomething();
}
