package com.book.rental.rental.service;

import com.book.rental.rental.domain.entity.Rental;

public interface RentalService {

	Rental rentBook(Long userId, Long bookId, String bookTitle);

	Rental returnBooks(Long userId, Long bookId);

	Long beOverdueBook(Long renalId, Long bookId);

	Rental returnOverdueBook(Long userId, Long book);

	Rental releaseOverdue(Long userId);
}
