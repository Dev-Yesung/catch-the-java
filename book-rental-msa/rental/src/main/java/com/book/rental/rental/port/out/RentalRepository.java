package com.book.rental.rental.port.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.rental.rental.domain.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
	Optional<Rental> findByUserId(Long userId);
}
