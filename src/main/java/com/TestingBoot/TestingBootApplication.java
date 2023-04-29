package com.TestingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestingBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingBootApplication.class, args);
	}

}
