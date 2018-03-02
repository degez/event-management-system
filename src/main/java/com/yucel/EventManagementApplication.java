package com.yucel;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.iyzipay.model.Locale;
import com.yucel.service.BinNumberChecker;

@SpringBootApplication()
//		scanBasePackages = { "com.yucel.service.impl", "com.yucel.util", "com.yucel.controller",
//		"com.yucel.resource" })
@PropertySource(value = "classpath:application.properties")
public class EventManagementApplication {

	@Autowired
	Environment environment;

	@Autowired
	ApplicationContext context;

	@Autowired
	BinNumberChecker binNumberChecker;

	public static void main(String[] args) {
		SpringApplication.run(EventManagementApplication.class, args);
	}

	@PostConstruct
	public void init() {

		System.out.println(binNumberChecker.checkAndRetrieveBinNumber(Locale.TR, "1231231231", "589004"));
	}
}
