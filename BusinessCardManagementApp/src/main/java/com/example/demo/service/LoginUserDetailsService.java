package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginUserDetails;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService{
	
	private final LoginUserRepository loginUserRepository;
	
	public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
		this.loginUserRepository = loginUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		Optional<LoginUser> loginUserOptional = loginUserRepository.findByEmail(email);
		return loginUserOptional.map(loginUser -> new LoginUserDetails(loginUser)).orElseThrow(() -> new UsernameNotFoundException("not found"));
	}
}
