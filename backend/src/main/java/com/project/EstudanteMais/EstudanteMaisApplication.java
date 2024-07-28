package com.project.EstudanteMais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EstudanteMaisApplication {
	public static void main(String[] args) {
		SpringApplication.run(EstudanteMaisApplication.class, args);
	}
}
