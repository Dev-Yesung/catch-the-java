package com.book.rental.rental.adapter.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/health")
@RestController
public class HealthController {

	@GetMapping
	public String checkHealth() {
		return "Book Server is up";
	}
}
