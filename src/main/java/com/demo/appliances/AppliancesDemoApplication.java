package com.demo.appliances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
@Retryable
public class AppliancesDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppliancesDemoApplication.class, args);
	}

}
