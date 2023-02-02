package com.ssafy.redistest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RedisTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisTestApplication.class, args);
	}

}
