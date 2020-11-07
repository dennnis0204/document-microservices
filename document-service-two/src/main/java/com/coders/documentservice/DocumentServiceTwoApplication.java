package com.coders.documentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DocumentServiceTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentServiceTwoApplication.class, args);
	}

}
