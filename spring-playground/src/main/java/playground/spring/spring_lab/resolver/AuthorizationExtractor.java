package playground.spring.spring_lab.resolver;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {
	T extract(HttpServletRequest request);
}
