package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot Initializer Class
 */

@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	@Autowired
	Runnable MessageListener;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean(name="schedulingRunner")
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
	    return new CommandLineRunner() {
	        public void run(String... args) throws Exception {
	            executor.execute(MessageListener);
	        }
	    };
	}
}
