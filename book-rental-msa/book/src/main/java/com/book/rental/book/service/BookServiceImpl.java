package com.book.rental.book.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@Transactional
	@Override
	public Book findBookInfo(Long bookId) {
		return bookRepository.findById(bookId).get();
	}
}
