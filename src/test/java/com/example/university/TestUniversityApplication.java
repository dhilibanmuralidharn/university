package com.example.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestUniversityApplication {

	public static void main(String[] args) {
		SpringApplication.from(UniversityApplication::main).with(TestUniversityApplication.class).run(args);
	}

}
