package com.yoursoft.redisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisappApplication.class, args);
	}



}
