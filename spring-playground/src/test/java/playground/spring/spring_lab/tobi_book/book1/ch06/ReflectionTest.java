package playground.spring.spring_lab.tobi_book.book1.ch06;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionTest {

	@DisplayName("자바 리플렉션으로 String의 length() 메소드를 호출한다.")
	@Test
	void test1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		// given
		String name = "Spring";

		// when
		Method lengthMethod = String.class.getMethod("length");
		Method charAtMethod = String.class.getMethod("charAt", int.class);

		// then
		assertThat((Integer)lengthMethod.invoke(name)).isEqualTo(6);
		assertThat((Character)charAtMethod.invoke(name, 0)).isEqualTo('S');
	}

	@DisplayName("프록시 테스트")
	@Test
	void test2() {
		// given
		Hello proxiedHello = new HelloUppercase(new HelloTarget());

		// when
		String result1 = proxiedHello.sayHello("Toby");
		String result2 = proxiedHello.sayHi("Toby");
		String result3 = proxiedHello.sayThankYou("Toby");

		// then
		assertThat(result1).isEqualTo("HELLO TOBY");
		assertThat(result2).isEqualTo("HI TOBY");
		assertThat(result3).isEqualTo("THANK YOU TOBY");
	}



	@DisplayName("")
	@Test
	void test3() {

		// given
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),
			new Class[] {Hello.class}, new UppercaseHandler(new HelloTarget()));
		// when

		// then
	}
}
