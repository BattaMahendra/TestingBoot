package com.TestingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableCaching
@RefreshScope
public class TestingBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingBootApplication.class, args);
	}

}
