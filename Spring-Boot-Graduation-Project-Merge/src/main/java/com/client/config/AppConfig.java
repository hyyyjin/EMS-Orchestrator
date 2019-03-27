package com.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * application main task executer
 */
@Component
public class AppConfig {

	@Primary
	@Bean(name="taskExecutor")
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
