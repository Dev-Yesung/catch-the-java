package com.book.rental.rental.domain.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "returned_item")
@Entity
public class ReturnedItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "book_id")
	private Long bookId;

	@Column(name = "returned_date")
	private LocalDate returnedDate;

	@Column(name = "book_title")
	private String bookTitle;

	public static ReturnedItem createReturnedItem(Long bookId, String bookTitle, LocalDate now) {
		ReturnedItem returnedItem = new ReturnedItem();
		returnedItem.setBookId(bookId);
		returnedItem.setBookTitle(bookTitle);
		returnedItem.setReturnedDate(now);
		return returnedItem;
	}
}
