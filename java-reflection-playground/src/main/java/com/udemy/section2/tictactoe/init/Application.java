package com.udemy.section2.tictactoe.init;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.udemy.section2.tictactoe.game.Game;
import com.udemy.section2.tictactoe.game.internal.TicTacToeGame;

public class Application {

	public static void main(String[] args) throws
		IllegalAccessException,
		InstantiationException,
		InvocationTargetException {
		Game game = createObjectRecursively(TicTacToeGame.class);
		game.start();
	}

	public static <T> T createObjectRecursively(Class<T> clazz) throws
		IllegalAccessException,
		InvocationTargetException,
		InstantiationException {
		Constructor<?> constructor = getFirstConstructor(clazz);

		List<Object> constructorArguments = new ArrayList<>();

		for (Class<?> argumentType : constructor.getParameterTypes()) {
			Object argumentValue = createObjectRecursively(argumentType);
			constructorArguments.add(argumentValue);
		}

		constructor.setAccessible(true);
		return (T)constructor.newInstance(constructorArguments.toArray());
	}

	private static Constructor<?> getFirstConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		if (constructors.length == 0) {
			throw new IllegalStateException(
				String.format("No constructor has been found for class %s", clazz.getName()));
		}

		return constructors[0];
	}
}
