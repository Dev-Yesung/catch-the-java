package playground.spring.spring_lab.bean_lifecycle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TestController {

	@GetMapping("/get/test")
	public ResponseEntity<String> getTest(
		@RequestHeader String customField,
		@RequestParam String userName
	) {
		StringBuilder sb = new StringBuilder();

		sb.append("custom-field : ").append(customField)
			.append('\n')
			.append("user-name : ").append(userName);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(sb.toString());
	}

}
