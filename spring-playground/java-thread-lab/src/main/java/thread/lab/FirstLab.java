package thread.lab;

import java.util.List;
import java.util.stream.IntStream;

public class FirstLab {
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		List<Thread> threads = IntStream.range(0, 1_000_000)
			.mapToObj(i -> new Thread(() -> {
			}))
			.toList();

		threads.forEach(Thread::start);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		System.out.println("기존 Thread 실행시간 : " + duration + "ms");

		// Virtual Thread
		startTime = System.nanoTime();
		List<Thread> virtualThreads = IntStream.range(0, 1_000_000)
			.mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
			}))
			.toList();
		virtualThreads.forEach(Thread::start);
		endTime = System.nanoTime();
		duration = (endTime - startTime) / 1_000_000;
		System.out.println("Virtual Thread 실행시간 : " + duration + "ms");

	}
}
