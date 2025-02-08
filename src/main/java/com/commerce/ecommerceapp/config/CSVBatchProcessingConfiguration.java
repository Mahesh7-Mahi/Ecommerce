package com.commerce.ecommerceapp.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class CSVBatchProcessingConfiguration {
	
	@Bean("CSVBatchProcessing")
	public Executor csvFileBatchProccessing() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(15);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(5);
		executor.setThreadNamePrefix("CSVBatchProcessingTask-");
		executor.initialize();
		return executor;
	}

}
