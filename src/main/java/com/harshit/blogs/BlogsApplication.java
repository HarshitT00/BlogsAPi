package com.harshit.blogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan("com.harshit.blogs")
public class BlogsApplication {

	public static void main(String[] args) throws RuntimeException {
		SpringApplication.run(BlogsApplication.class, args);
	}

}
