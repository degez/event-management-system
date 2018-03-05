package com.yucel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Main class of the project
 * 
 * @author yucel.ozan
 *
 */
@SpringBootApplication()
@PropertySource(value = "classpath:application.properties")
@PropertySource(value = "file:${config.dir}", ignoreResourceNotFound = true)
public class EventManagementApplication {

	private static Logger logger = LoggerFactory.getLogger(EventManagementApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(EventManagementApplication.class, args);
		logger.info("application has been started");
	}

}
