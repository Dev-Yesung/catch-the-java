package com.book.rental.rental.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.rental.rental.domain.entity.Rental;
import com.book.rental.rental.dto.BookInfoDTO;
import com.book.rental.rental.dto.LateFeeDTO;
import com.book.rental.rental.port.out.BookClient;
import com.book.rental.rental.service.RentalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class RentalController {

	private final BookClient bookClient;
	private final RentalService rentalService;

	@PostMapping("/rentals/{userId}/rental-item/{bookId}")
	public ResponseEntity<RentalDTO> rentBook(@PathVariable("userId") Long userId,
		@PathVariable("bookId") Long bookId) {
		ResponseEntity<BookInfoDTO> bookInfoResponse = bookClient.findBookInfo(bookId);
		BookInfoDTO bookInfoDTO = bookInfoResponse.getBody();

		Rental rental = rentalService.rentBook(userId, bookInfoDTO.getId(), bookInfoDTO.getTitle());

		RentalDTO rentalDTO = RentalDTO.toDTO(rental);

		return ResponseEntity.ok(rentalDTO);
	}

	@DeleteMapping("/rentals/{userId}/rented-item/{bookId}")
	public ResponseEntity<RentalDTO> returnBook(@PathVariable("userId") Long userId,
		@PathVariable("bookId") Long bookId) {
		Rental rental = rentalService.returnBooks(userId, bookId);
		RentalDTO rentalDTO = RentalDTO.toDto(rental);

		return ResponseEntity.ok(rentalDTO);
	}

	@PutMapping("/rentals/release-overdue/user/{userId}")
	private ResponseEntity releaseOverdue(@PathVariable("userId") Long userId) {
		Long lateFee = rentalService.findLateFee(userId);

		LateFeeDTO lateFeeDTO = new LateFeeDTO(userId, lateFee);
		try {
			userClient.usePoint(lateFeeDTO);
		} catch (FeignClientException e) {
			if (!Integer.valueOf(HttpStatus.NOT_FOUND.value()).equals(e.getStatus())) {
				throw e;
			}
		}

		Rental rental = rentalService.releaseOverdue(userId);
		RentalDTO rentalDTO = RentalDTO.from(rental);

		return ResponseEntity.ok(rentalDTO);
	}

}
