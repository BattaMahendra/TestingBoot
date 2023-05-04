package com.TestingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@RefreshScope
@EnableSwagger2
public class TestingBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingBootApplication.class, args);
	}

}
