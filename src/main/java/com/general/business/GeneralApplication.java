package com.general.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
public class GeneralApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneralApplication.class, args);
	}

}
