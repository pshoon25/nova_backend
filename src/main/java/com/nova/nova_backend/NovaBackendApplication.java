package com.nova.nova_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NovaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaBackendApplication.class, args);
	}

}
