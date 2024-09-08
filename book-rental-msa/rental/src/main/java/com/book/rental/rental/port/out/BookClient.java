package com.book.rental.rental.port.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.book.rental.rental.dto.BookInfoDTO;

@FeignClient(name = "book",
	configuration = {FeignClientProperties.FeignClientConfiguration.class})
public interface BookClient {

	@GetMapping("/api/books/book-info/{bookId}")
	ResponseEntity<BookInfoDTO> findBookInfo(@PathVariable("bookId") Long bookId);
}
