package playground.spring.spring_lab.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

	private final AuthorizationExtractor<AuthInfo> authorizationExtractor = new BasicAuthorizationExtractor();

	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		// 현재 파라미터를 리졸버가 지원하는지에 대한 여부에 따라 true/false 값을 반환한다.
		// 아래 코드에서는 해당 파라미터가 @AuthenticationPrincipal 애너테이션을 포함하는지 여부에 따라 boolean 값을 리턴하고 있다.
		return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
	}

	@Override
	public Object resolveArgument(
		final MethodParameter parameter,
		final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest,
		final WebDataBinderFactory binderFactory
	) throws Exception {
		// 실제로 바인딩할 객체를 리턴한다.
		// 아래 코드에서는 request 내 Authorization 헤더에서 추출한 AuthInfo 를 리턴하고 있다.
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();

		return authorizationExtractor.extract(request);
	}
}
