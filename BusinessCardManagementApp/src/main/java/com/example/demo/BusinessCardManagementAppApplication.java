package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.LoggingFilter;

@SpringBootApplication
public class BusinessCardManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessCardManagementAppApplication.class, args);
	}

    @Bean
    FilterRegistrationBean<?> loggingFilter(){
		FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>(new LoggingFilter());
		registrationBean.setOrder(Integer.MIN_VALUE);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
