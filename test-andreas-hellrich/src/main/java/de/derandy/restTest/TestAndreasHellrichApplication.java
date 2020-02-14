package de.derandy.restTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestAndreasHellrichApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "csv");
		SpringApplication.run(TestAndreasHellrichApplication.class, args);
	}

}
