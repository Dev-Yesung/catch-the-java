package com.udemy.section2;

public class ThreadCreation1 {

	// Java 8이상은 람다로 Runnable을 구현할 수 있음
	static Runnable task = () -> {
		throw new RuntimeException("[ 의도적인 런타임 에러 ]");
	};
	static Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> {
		System.out.println("치명적 에러 발생: " + t.getName()
						   + "에서 발생한 에러는 " + e.getMessage() + "입니다.");
	};

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(task);
		// 스레드에 특정한 이름을 부여할 수 있다 -> 디버깅에 용이
		thread.setName("Misbehaving Thread");
		// 스레드의 스케줄링 우선순위를 결정할 수 있다.
		// thread.setPriority(Thread.MAX_PRIORITY);

		// System.out.println("현재 스레드: " + Thread.currentThread().getName() + "\n새로운 스레드를 시작하기 전 입니다.");
		thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
		thread.start();
		// System.out.println("현재 스레드: " + Thread.currentThread().getName() + "\n새로운 스레드를 시작한 후 입니다.");
		// Thread.sleep(10000);
	}
}
