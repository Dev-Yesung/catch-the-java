package playground.spring.spring_lab.resolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.http.HttpServletRequest;

public class BasicAuthorizationExtractor
	implements AuthorizationExtractor<AuthInfo> {

	private static final Map<Long, AuthInfo> inMemoryAuthInfo = new ConcurrentHashMap<>();

	public BasicAuthorizationExtractor() {
		for (long i = 1; i <= 10; i++) {
			inMemoryAuthInfo.put(i, new AuthInfo(i, i + "@test.tc", "test" + i, "test-user" + i));
		}
	}

	@Override
	public AuthInfo extract(HttpServletRequest request) {
		Long userId = Long.parseLong(request.getHeader("Authorization"));

		return inMemoryAuthInfo.get(userId);
	}
}
