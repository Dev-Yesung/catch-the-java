package playground.spring.spring_lab.bean_lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanLifeCycle implements InitializingBean, DisposableBean {

	private String property;

	public BeanLifeCycle(String property) {
		this.property = property;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TodoSomething...
		System.out.println("객체의 속성이 모두 세팅된 후에 실행됩니다.");
	}

	public void send() {
		System.out.println("생성자 방식으로 설정된 property 값 : " + property);
	}

	@Override
	public void destroy() throws Exception {
		// TodoSomething...
		System.out.println("이 메서드는 빈이 소멸되기 전에 실행됩니다.");
	}

}
