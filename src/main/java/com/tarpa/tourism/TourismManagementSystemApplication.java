package com.tarpa.tourism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TourismManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				TourismManagementSystemApplication.class,
				args
		);
	}
}