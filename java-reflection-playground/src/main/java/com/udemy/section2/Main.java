package com.udemy.section2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Main {
	public static void main(String[] args) throws
		InvocationTargetException, InstantiationException, IllegalAccessException {
		// printConstructorData(Address.class);
		// Person person = (Person)createInstanceWithArgument(Person.class);
		// Person person = (Person)createInstanceWithArgument(Person.class, "John", 20);
		Address address = createInstanceWithArgument(Address.class, "First Street", 10);
		Person person = createInstanceWithArgument(Person.class, address, "John", 20);
		System.out.println(person);
	}

	public static <T> T createInstanceWithArgument(Class<T> clazz, Object... args) throws
		InvocationTargetException, InstantiationException, IllegalAccessException {
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == args.length) {
				return (T)constructor.newInstance(args);
			}
		}

		System.out.println("An appropriate constructor was not found");
		return null;
	}

	public static void printConstructorData(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();

		System.out.println(String.format("class %s has %d declared constructors",
			clazz.getSimpleName(), constructors.length));

		for (Constructor<?> constructor : constructors) {
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			List<String> parameterTypeNames = Arrays.stream(parameterTypes)
				.map(Class::getSimpleName)
				.toList();
			System.out.println(parameterTypeNames);
		}
	}

	public static class Person {
		private final Address address;
		private final String name;
		private final int age;

		public Person() {
			this(null, "anonymous", 0);
		}

		public Person(String name) {
			this(null, name, 0);
		}

		public Person(String name, int age) {
			this(null, name, age);
		}

		public Person(Address address, String name, int age) {
			this.address = address;
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("address", address)
				.append("name", name)
				.append("age", age)
				.toString();
		}
	}

	public static class Address {
		private final String street;
		private final int number;

		public Address(String street, int number) {
			this.street = street;
			this.number = number;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("street", street)
				.append("number", number)
				.toString();
		}
	}
}
