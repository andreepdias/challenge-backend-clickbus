package io.github.andreepdias.clickbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class ClickbusApplication {

	@Bean
	public Clock systemDefaultlock() { // <--Note the method name will change the bean ID
		return Clock.systemDefaultZone();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClickbusApplication.class, args);
	}

}
