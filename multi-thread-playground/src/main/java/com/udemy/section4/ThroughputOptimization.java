package com.udemy.section4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Throughput(처리량) 최적화 예시<br>
 * 클라이언트가 HTTP 요청 URL에 있는 어떤 단어를 전송하면<br>
 * 애플리케이션은 책에서 단어를 검색하고<br>
 * 단어 등장 횟수를 센 다음 사용자에게 수를 전송한다.<br>
 * throughput optimization은 latency optimization과 달리<br>
 * 가상 스레드 수까지 늘릴 수 있는데,<br>
 * 그 이유는 latency optimization에서 들었던 추가적인 비용들이 덜 들기 때문이다.
 */
public class ThroughputOptimization {
	private static final String INPUT_FILE = "./resources/war_and_peace.txt";
	private static final int NUMBER_OF_THREADS = 4;

	public static void main(String[] args) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
		startServer(text);
	}

	public static void startServer(String text) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		server.setExecutor(executor);

		server.createContext("/search", new WordCountHandler(text));

		server.start();
	}

	private static class WordCountHandler implements HttpHandler {
		private final String text;

		public WordCountHandler(String text) {
			this.text = text;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String query = exchange.getRequestURI().getQuery();
			String[] keyValue = query.split("=");
			String action = keyValue[0];
			String word = keyValue[1];

			if (!action.equals("word")) {
				exchange.sendResponseHeaders(400, 0);
				return;
			}

			long count = countWord(word);
			byte[] response = Long.toString(count).getBytes();
			exchange.sendResponseHeaders(200, response.length);
			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response);
			outputStream.close();
		}

		private long countWord(String word) {
			long count = 0;
			int index = 0;
			while (index >= 0) {
				index = text.indexOf(word, index);

				if (index >= 0) {
					count++;
					index++;
				}
			}
			return count;
		}
	}
}
