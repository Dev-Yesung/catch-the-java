package com.book.rental.book.adapter.in;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import com.book.rental.book.event.StockChanged;
import com.book.rental.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookConsumer {

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private static final String TOPIC = "topic_book";
	private KafkaProperties kafkaProperties;
	private KafkaConsumer<String, String> kafkaConsumer;
	private BookService bookService;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void initialize() {
		kafkaConsumer = new KafkaConsumer<>(kafkaProperties.getConsumerProps());
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
		kafkaConsumer.subscribe(Collections.singleton(TOPIC));

		executorService.execute(() -> {
			try {
				while (!closed.get()) {
					ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
					for (ConsumerRecord<String, String> record : records) {
						StockChanged stockChanged = objectMapper.readValue(record.value(), StockChanged.class);
						bookService.processChangeBookState(stockChanged.bookId(), stockChanged.bookStatus());
					}
				}
				kafkaConsumer.commitSync();
			} catch (WakeupException e) {
				if (!closed.get()) {
					throw e;
				}
			} catch (Exception e) {

			} finally {
				kafkaConsumer.close();
			}
		});
	}
}
