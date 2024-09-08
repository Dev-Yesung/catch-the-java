package com.book.rental.book.event;

public record StockChanged(
	Long bookId,
	String bookStatus
) {
}
