package com.commerce.ecommerceapp.config;


import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

@SuppressWarnings({"rawtypes","unchecked"})
@Configuration
public class LogConfiguration {
	
	@Bean
	public Logger getLogger() {
		
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		Logger rootLogger = loggerContext.getLogger("com.commerce.ecommerceapp");
		
		rootLogger.setLevel(Level.INFO);
		
		RollingFileAppender fileAppender = new RollingFileAppender();
		
		fileAppender.setContext(loggerContext);
		fileAppender.setName("FILE");
		fileAppender.setFile("logs/application.log");
		
		TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
		
		timeBasedRollingPolicy.setContext(loggerContext);
		timeBasedRollingPolicy.setParent(fileAppender);
		timeBasedRollingPolicy.setFileNamePattern("logs/application-%d{yyyy-MM-dd}.log");
		timeBasedRollingPolicy.setMaxHistory(30);
		timeBasedRollingPolicy.start();
		
		
		//Defining Pattern
		
		PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
		
		patternLayoutEncoder.setContext(loggerContext);
		patternLayoutEncoder.setPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] %-5level %logger{36}-%msg%n");
		patternLayoutEncoder.start();
		
		fileAppender.setEncoder(patternLayoutEncoder);
		fileAppender.setRollingPolicy(timeBasedRollingPolicy);
		fileAppender.start();
		
		rootLogger.addAppender(fileAppender);
		
		return rootLogger;
		
	
	}

}
