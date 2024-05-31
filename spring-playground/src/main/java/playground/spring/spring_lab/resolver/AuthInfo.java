package playground.spring.spring_lab.resolver;

public record AuthInfo(
	Long userId,
	String email,
	String password,
	String nickName
) {
}
