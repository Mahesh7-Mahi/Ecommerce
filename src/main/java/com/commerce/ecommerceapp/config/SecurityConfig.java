package com.commerce.ecommerceapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.commerce.ecommerceapp.filter.JwtRequestFilter;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * public apis -> can access without token
	 * secured apis -> with token
	 * rolbased -> admin, student
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf( csrf -> csrf.disable() )
					.authorizeHttpRequests( auth ->
						auth
//							.requestMatchers("/login").permitAll()
//							.requestMatchers("/signup").permitAll()
							.requestMatchers("/auth/**").permitAll()
							.requestMatchers("/actuator/**").permitAll()
							.requestMatchers("/instructor/**").permitAll()
							.requestMatchers("/customers/**").hasRole("CUSTOMER")
							.requestMatchers("/admin/**").hasRole("ADMIN")
							.anyRequest().authenticated()
					)
		.sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
		
	}
}
