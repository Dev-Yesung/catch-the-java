package ch06;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TvTest {
	private static Tv tv;
	@BeforeAll static void initialize() {
		tv = new Tv();
	}

	@DisplayName("초기 전원 테스트") @Test void tvPowerTest() {
		Assertions.assertThat(tv.toString()).contains("전원상태 : ").contains("false");
	}
}
