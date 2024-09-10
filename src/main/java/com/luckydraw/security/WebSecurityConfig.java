package com.luckydraw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic();

		// web.ignoring().antMatchers("/api/v1/signup");

		http.authorizeHttpRequests(requests -> requests
				// .anyRequest().authenticated();
				.requestMatchers("/admin/**").permitAll().requestMatchers("/user/**").permitAll()
				.requestMatchers("/contest/**").permitAll().requestMatchers("/usercontestlist/**").permitAll()
				.requestMatchers("/joincontest/**").permitAll())
				.csrf(csrf -> csrf.disable());

		return http.build();
	}
}
