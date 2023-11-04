package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.mindSyncService;


//@EnableMongoRepositories(basePackageClasses = savePostRepository.class)
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
public class MindSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindSyncApplication.class, args);
	}
	
	  @Bean
	  public mindSyncService mindSyncServiceBean() {
	        return new mindSyncService();
	  }
	  
	  @LoadBalanced
	  @Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}



}
