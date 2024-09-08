package com.book.rental.book.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.book.rental.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookController {

	private final BookService bookService;

	@GetMapping("/books/bookInfo/{bookId}")
	public ResponseEntity<BookInfoDTO> findBookInfo(@PathVariable("bookId") Long bookId) {
		Book book = bookService.findBookInfo(bookId);
		BookInfoDTO bookInfoDTO = new BookInfoDTO(bookId, book.getTitle());

		return ResponseEntity.ok(bookInfoDTO);
	}
}
