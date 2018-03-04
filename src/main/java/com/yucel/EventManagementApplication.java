package com.yucel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication()
@PropertySource(value = "classpath:application.properties")
public class EventManagementApplication {

	private static Logger logger = LoggerFactory.getLogger(EventManagementApplication.class);
	
	@Autowired
	Environment environment;

	@Autowired
	ApplicationContext context;


	public static void main(String[] args) {
		
		SpringApplication.run(EventManagementApplication.class, args);
		logger.info("application has been started");
	}

}
