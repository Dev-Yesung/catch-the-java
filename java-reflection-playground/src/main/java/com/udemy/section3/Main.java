package com.udemy.section3;

import java.lang.reflect.Field;

public class Main {

	public static void main(String[] args) throws IllegalAccessException {
		// printDeclaredFieldsInfo(Movie.class);
		// printDeclaredFieldsInfo(Movie.MovieStats.class);
		// printDeclaredFieldsInfo(Category.class);
		Movie movie = new Movie("Lord of the Rings",
			2001,
			12.99,
			true,
			Category.ADVENTURE);
		printDeclaredFieldsInfo(movie.getClass(), movie);
	}

	public static <T> void printDeclaredFieldsInfo(Class<? extends T> clazz, T instance) throws IllegalAccessException {
		for (Field field : clazz.getDeclaredFields()) {
			System.out.printf("Field name : %s type : %s%n",
				field.getName(), field.getType().getName());
			System.out.printf("Is synthetic field : %s%n", field.isSynthetic());
			System.out.printf("Field value is : %s%n", field.get(instance));

			System.out.println();
		}
	}

	public static class Movie extends Product {
		public static final double MINIMUM_PRICE = 10.99;
		private boolean isReleased;
		private Category category;
		private double actualPrice;

		public Movie(
			String name,
			int year,
			double price,
			boolean isReleased,
			Category category
		) {
			super(name, year);
			this.isReleased = isReleased;
			this.category = category;
			this.actualPrice = Math.max(price, MINIMUM_PRICE);
		}

		// 중첩된 비정적 클래스는 부모 클래스 Movie에서 정의된
		// actualPrice 변수에 접근할 수 있다.
		// -> 즉 눈에 보이지 않는 연결관계가 있다.
		public class MovieStats {
			private double timeWatched;

			public MovieStats(double timeWatched) {
				this.timeWatched = timeWatched;
			}

			public double getRevenue() {
				return timeWatched * actualPrice;
			}
		}
	}

	public static class Product {
		protected String name;
		protected int year;
		protected double actualPrice;

		public Product(String name, int year) {
			this.name = name;
			this.year = year;
		}
	}

	public enum Category {
		ADVENTURE,
		ACTION,
		COMEDY
	}
}
