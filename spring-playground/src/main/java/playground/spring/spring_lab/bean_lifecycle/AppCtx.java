package playground.spring.spring_lab.bean_lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCtx {

	@Bean
	public BeanLifeCycle beanLifeCycle() {
		System.out.println("빈 객체를 생성합니다...");
		BeanLifeCycle beanLifeCycle = new BeanLifeCycle("빈 라이프 사이클!!!");
		System.out.println("빈 객체를 생성이 모두 완료되어 리턴합니다...");

		return beanLifeCycle;
	}

}
