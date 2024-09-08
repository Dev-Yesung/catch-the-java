package com.book.rental.rental.event;

public record StockChanged(
	Long bookId,
	String bookStatus
) {
}
