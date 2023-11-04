package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class MindSyncUserPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindSyncUserPostApplication.class, args);
	}
	

//	@Bean
//	public userPostService userPostServiceBean() {
//		return new userPostService();
//	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}


