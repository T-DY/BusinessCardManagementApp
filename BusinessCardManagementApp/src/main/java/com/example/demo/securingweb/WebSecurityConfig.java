package com.example.demo.securingweb;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.formLogin(login -> login
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error")
				.permitAll()
			).logout(logout -> logout
				.logoutSuccessUrl("/")
			).authorizeHttpRequests(authz -> authz
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/static/css/**").permitAll() //たまにCSSのファイル部分にリダイレクトされるのでCSS自体を制限解除
				.requestMatchers("/general").hasAnyRole("GENERAL","ADMIN")
				.requestMatchers("/businesscard/**").hasAnyRole("GENERAL","ADMIN")
				.requestMatchers("/businesscard/upload").hasAnyRole("GENERAL","ADMIN")
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/user/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			);
		return http.build();
	}
    
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
    	return new BCryptPasswordEncoder();
    }
}
