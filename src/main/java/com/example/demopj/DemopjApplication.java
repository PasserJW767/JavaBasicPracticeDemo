package com.example.demopj;

import com.example.demopj.aboutInterview.listener.business.OrderCreateEvent;
import com.example.demopj.aboutInterview.listener.business.OrderService;
import com.example.demopj.interceptor.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Iterator;

@SpringBootApplication
@Import(WebConfig.class)
public class DemopjApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemopjApplication.class, args);
	}

}
