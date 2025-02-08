package com.commerce.ecommerceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IpAddressInfoConfig {
	
	@Bean("restTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
