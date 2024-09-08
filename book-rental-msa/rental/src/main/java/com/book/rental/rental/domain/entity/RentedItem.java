package com.book.rental.rental.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "rented_item")
@Entity
public class RentedItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "book_id")
	private Long bookId;

	@Column(name = "rented_date")
	private LocalDate rentedDate;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "book_title")
	private String bookTitle;

	@ManyToOne
	@JsonIgnoreProperties("rentedItems")
	private Rental rental;

	public static RentedItem createRentedItem(Long bookId, String bookTitle, LocalDate rentedDate) {
		RentedItem rentedItem = new RentedItem();
		rentedItem.setId(bookId);
		rentedItem.setBookTitle(bookTitle);
		rentedItem.setRentedDate(rentedDate);
		rentedItem.setDueDate(rentedDate.plusWeeks(2));
		return rentedItem;
	}
}
