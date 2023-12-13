package com.udemy.section3;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {
	public static void main(String[] args) {
		List<Long> inputNumbers = Arrays.asList(10000000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5556L);
		List<FactorialThread> factorialThreads = inputNumbers.stream()
			.map(FactorialThread::new)
			.toList();

		factorialThreads.stream()
			.parallel()
			.forEach(thread -> {
				thread.setDaemon(true);
				thread.start();
				try {
					thread.join(2000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

		factorialThreads.stream()
			.parallel()
			.forEach(thread -> {
				if (thread.isFinished()) {
					System.out.println("Factorial of " + thread.getInputNumber() + " is " + thread.getResult());
				} else {
					System.out.println("The calculation for " + thread.getInputNumber() + " is still in progress");
					System.out.println("Interrupt thread because of calculating for long times...");
					thread.interrupt();
				}
			});
	}

	public static class FactorialThread extends Thread {
		private final long inputNumber;
		private BigInteger result = BigInteger.ZERO;
		private boolean isFinished = false;

		public FactorialThread(long inputNumber) {
			this.inputNumber = inputNumber;
		}

		@Override
		public void run() {
			this.result = factorial(inputNumber);
			this.isFinished = true;
		}

		public BigInteger factorial(long n) {
			BigInteger tempResult = BigInteger.ONE;

			for (long i = n; i > 0; i--) {
				if (currentThread().isInterrupted()) {
					return BigInteger.ZERO;
				}
				tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
			}
			return tempResult;
		}

		public long getInputNumber() {
			return inputNumber;
		}

		public BigInteger getResult() {
			return result;
		}

		public boolean isFinished() {
			return isFinished;
		}
	}
}
