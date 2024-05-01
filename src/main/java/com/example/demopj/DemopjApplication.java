package com.example.demopj;

import com.example.demopj.interceptor.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
public class DemopjApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemopjApplication.class, args);
	}

}
