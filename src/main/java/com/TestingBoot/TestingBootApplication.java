package com.TestingBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
        exclude = {
                SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class
        })
@EnableDiscoveryClient
@EnableCaching
@RefreshScope
@ComponentScan(basePackages = "com.security")
public class TestingBootApplication {
	
	//just added for testing jenkins build.
	int dummyVal;

	public static void main(String[] args) {
		SpringApplication.run(TestingBootApplication.class, args);
	}

}
