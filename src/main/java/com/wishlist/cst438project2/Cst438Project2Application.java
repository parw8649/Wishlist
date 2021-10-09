package com.wishlist.cst438project2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Cst438Project2Application {

	@GetMapping("/")
	public String healthCheck() {
		return "Service is running!";
	}

	public static void main(String[] args) {
		SpringApplication.run(Cst438Project2Application.class, args);
	}
}
