package com.book.rental.rental.adapter.out;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import com.book.rental.rental.port.out.RentalProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RentalProducerImpl implements RentalProducer {

	private static final String TOPIC_BOOK = "topic_book";
	private static final String TOPIC_CATALOG = "topic_catalog";
	private static final String TOPIC_POINT = "topic_point";

	private final KafkaProperties kafkaProperties;
	private KafkaProducer<String, String> producer;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void initialize() {
		this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
		Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	@Override
	public void updateBookStatus(Long bookId, String bookStatus) {
		StockChanged stockChanged = new StockChanged(bookId, bookStatus);
		String message = objectMapper.writeValueAsString(stockChanged);
		producer.send(new ProducerRecord<>(TOPIC_BOOK, message)).get();
	}

	@Override
	public void savePoints(Long userId, int points) {
		PointChanged pointChanged = new PointChanged(userId, points);
		String message = objectMapper.writeValueAsString(pointChanged);
		producer.send(new ProducerRecord<>(TOPIC_POINT, message)).get();
	}

	@Override
	public void updateBookCatalogStatus(Long bookId, String eventType) {
		BookCatalogChanged bookCatalogChanged = new BookCatalogChanged();
		bookCatalogChanged.setBookId(bookId);
		bookCatalogChanged.setEventType(eventType);
		String message = objectMapper.writeValueAsString(bookCatalogChanged);
		producer.send(new ProducerRecord<>(TOPIC_CATALOG, message)).get();
	}

	@PreDestroy
	private void shutdown() {
		producer.close();
	}

}
