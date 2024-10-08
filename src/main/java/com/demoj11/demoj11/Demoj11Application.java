package com.demoj11.demoj11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Demoj11Application {

	public static void main(String[] args) {
		SpringApplication.run(Demoj11Application.class, args);
	}

}
