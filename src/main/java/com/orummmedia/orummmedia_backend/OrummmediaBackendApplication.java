package com.orummmedia.orummmedia_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OrummmediaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrummmediaBackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigure(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // localhost:3030 뒤에 붙는 값을 상관없이 받겠다.
						.allowedHeaders("*")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:3000", "http://www.rs-nova.co.kr");
			}
		};
	}
}
