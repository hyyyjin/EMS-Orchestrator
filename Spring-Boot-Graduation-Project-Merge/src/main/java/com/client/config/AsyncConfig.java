package com.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

	/**
	 * thread pool for message service
	 * 
	 * @return
	 */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setQueueCapacity(20);
        taskExecutor.setThreadNamePrefix("Executor-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    /**
     * thread pool for DR algorithm task
     * 
     * @return
     */
    @Bean(name = "backgroundTaskExecutor")
    public Executor backgroundTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
//        taskExecutor.setMaxPoolSize(30);
//        taskExecutor.setQueueCapacity(20);
        taskExecutor.setThreadNamePrefix("Background-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}