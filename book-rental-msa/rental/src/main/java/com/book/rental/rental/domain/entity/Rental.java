package com.book.rental.rental.domain.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.book.rental.rental.domain.vo.RentalStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "rental")
@Entity
public class Rental {

	private static final int NUMBER_OF_MAXIMUM_BOOKS_TO_RENT = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(name = "rental_status")
	private RentalStatus rentalStatus;

	@Column(name = "late_fee")
	private Long lateFee;

	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RentedItem> rentedItems = new HashSet<>();

	@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<OverdueItem> overdueItems = new HashSet<>();

	@OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<ReturnedItem> returnedItems = new HashSet<>();

	public static Rental createRental(Long userId) {
		Rental rental = new Rental();
		rental.setId(userId);
		rental.setRentalStatus(RentalStatus.RENT_AVAILABLE);
		rental.setLateFee(0L);
		return rental;
	}

	public boolean checkRentalAvailable() {
		if (RentalStatus.RENT_UNAVAILABLE.equals(this.rentalStatus) || this.lateFee != 0) {
			throw new RentUnavailableException("연체 상태입니다. 연체료를 정산 후, 도서를 대출하실 수 있습니다.");
		}
		if (this.rentedItems.size() >= NUMBER_OF_MAXIMUM_BOOKS_TO_RENT) {
			throw new RentUnavailableException("대출 가능한 도서의 수는 %s권 입니다."
				.formatted(NUMBER_OF_MAXIMUM_BOOKS_TO_RENT - rentedItems.size()));
		}

		return true;
	}

	public void rentBook(Long bookId, String title) {
		this.addRentedItem(RentedItem.createRentedItme(bookId, title, LocalDate.now()));
	}

	public void returnBook(Long bookId) {
		RentedItem rentedItem = this.rentedItems.stream()
			.filter(item -> item.getBookId().equals(bookId))
			.findFirst()
			.get();
		this.addReturnedItem(ReturnedItem.createReturnedItem(rentedItem.getBookId(),
			rentedItem.getBookTitle(), LocalDate.now()));
		this.removeRentedItem(rentedItem);
	}

	public void overdueBook(Long bookId) {
		RentedItem rentedItem = this.rentedItems.stream()
			.filter(item -> item.getBookId().equals(bookId))
			.findFirst()
			.get();
		this.addOverdueItem(rentedItem);
	}

	public void returnOverdueBook(Long bookId) {
		OverdueItem overdueItem = this.overdueItems.stream()
			.filter(item -> item.getBookId().equals(bookId))
			.findFirst()
			.get();
		this.addReturnedItem(ReturnedItem.createReturnedItem(overdueItem.getBookId(),
			overdueItem.getBookTitle(),
			LocalDate.now()));
		this.removeOverdueItem(overdueItem);
	}

	public void makeRentUnable() {
		this.setRentalStatus(RentalStatus.RENT_UNAVAILABLE);
		this.setLateFee(this.getLateFee() + 30);
	}

	public void releaseOverdue() {
		this.setLateFee(0L);
		this.setRentalStatus(RentalStatus.RENT_AVAILABLE);
	}
}
