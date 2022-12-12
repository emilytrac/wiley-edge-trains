package com.gsix.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.gsix")
@EntityScan(basePackages = "com.gsix.entity")
@EnableJpaRepositories(basePackages = "com.gsix.persistence")
public class StationServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StationServiceApiApplication.class, args);
	}

}
