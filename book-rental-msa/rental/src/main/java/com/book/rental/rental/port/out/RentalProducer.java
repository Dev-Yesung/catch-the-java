package com.book.rental.rental.port.out;

public interface RentalProducer {

	void updateBookStatus(Long bookId, String unavailable);

	void savePoints(Long userId, int points);

	void updateBookCatalogStatus(Long bookId, String rentBook);
}
