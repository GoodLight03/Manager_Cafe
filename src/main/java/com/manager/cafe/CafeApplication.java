package com.manager.cafe;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}

	@Bean
	NewTopic notification(){
		return new NewTopic("notification", 1,(short)1);
		//,(short)2,,(short)3
	}

	@Bean
	NewTopic statistic(){
		return new NewTopic("statistic", 1,(short)1);
	}

}
