package com.commerce.ecommerceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SuppressWarnings("deprecation")
@Configuration
public class RedisConfig {

	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.activateDefaultTyping(
				BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
				ObjectMapper.DefaultTyping.EVERYTHING,
				JsonTypeInfo.As.PROPERTY
		);
		
		//objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		objectMapper.registerModule(new JavaTimeModule());
		
		objectMapper.findAndRegisterModules();
		
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		
		//redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
	
		return redisTemplate;
		
	}
	
}
