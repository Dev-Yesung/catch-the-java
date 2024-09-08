package com.book.rental.rental.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.rental.rental.domain.entity.Rental;
import com.book.rental.rental.port.out.RentalProducer;
import com.book.rental.rental.port.out.RentalRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService {

	private final RentalRepository rentalRepository;
	private final RentalProducer rentalProducer;

	@Transactional
	@Override
	public Rental rentBook(Long userId, Long bookId, String bookTitle) {
		Rental rental = rentalRepository.findByUserId(userId).get();
		rental.checkRentalAvailable();
		rental.rentBook(bookId, bookTitle);

		rentalProducer.updateBookStatus(bookId, "UNAVAILABLE");
		rentalProducer.updateBookCatalogStatus(bookId, "RENT_BOOK");
		rentalProducer.savePoints(userId);

		return rental;
	}

	@Transactional
	@Override
	public Rental returnBooks(Long userId, Long bookId) {
		Rental rental = rentalRepository.findByUserId(userId).get();
		rental.returnBook(bookId);

		rentalProducer.updateBookStatus(bookId, "AVAILABLE");
		rentalProducer.updateBookCatalogStatus(bookId, "RETURN_BOOK");

		return rental;
	}

	@Override
	public Long beOverdueBook(Long renalId, Long bookId) {
		Rental rental = rentalRepository.findById(renalId).get();
		rental.overdueBook(bookId);
		rental.makeRentUnable();

		return bookId;
	}

	@Override
	public Rental returnOverdueBook(Long userId, Long book) {
		Rental rental = rentalRepository.findByUserId(userId).get();
		rental.returnOverdueBook(book);
		rentalProducer.updateBookStatus(book, "AVAILABLE");
		rentalProducer.updateBookCatalogStatus(book, "RETURN_BOOK");

		return rental;
	}

	@Override
	public Rental releaseOverdue(Long userId) {
		Rental rental = rentalRepository.findByUserId(userId).get();
		rental.releaseOverdue();

		return rental;
	}
}
