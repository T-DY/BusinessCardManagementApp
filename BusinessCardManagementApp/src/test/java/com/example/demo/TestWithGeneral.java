package com.example.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Test
@WithUserDetails(userDetailsServiceBeanName = "loginUserDetailsService",
        value = "general@example.com")
public @interface TestWithGeneral {
}