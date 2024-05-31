package playground.spring.spring_lab.resolver;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/argument-test")
@RestController
public class TestApiController {

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(
		@PathVariable long userId,
		@AuthenticationPrincipal AuthInfo authInfo
	) {
		log.info("currentPath: {}", userId);
		log.info("email: {}, password: {}, nickName: {}",
			authInfo.email(), authInfo.password(), authInfo.nickName());

		Objects.requireNonNull(authInfo, "authInfo cannot be null");

		if (!authInfo.userId().equals(userId)) {
			throw new RuntimeException("Not authorized");
		}

		// do delete...

		return ResponseEntity.ok()
			.build();
	}
}
