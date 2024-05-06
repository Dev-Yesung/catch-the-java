package thread.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadTestController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(path = "/threads", method = RequestMethod.GET)
	public String tenSecSleepMethod() throws InterruptedException {
		log.info("Request start...");
		Thread.sleep(10000);
		log.info("Request end");

		return "ok";
	}

}
