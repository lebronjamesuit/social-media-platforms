package com.social.media.confessionmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ConfessionMediaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ConfessionMediaApplication.class, args);
	}

}
