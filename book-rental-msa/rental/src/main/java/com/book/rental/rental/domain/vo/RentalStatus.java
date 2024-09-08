package com.book.rental.rental.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RentalStatus {
	RENT_AVAILABLE(0, "대출가능", "대출가능상태"),
	RENT_UNAVAILABLE(1, "대출불가", "대출불가능상태");

	private final int value;
	private final String value1;
	private final String value2;
}
