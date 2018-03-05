package com.yucel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.iyzipay.Options;
import com.yucel.service.PaymentApiOptions;

@Service
@PropertySource(value = "classpath:application.properties")
@PropertySource(value = "file:${config.dir}", ignoreResourceNotFound = true)
public class IyzicoOptions extends Options implements EnvironmentAware, PaymentApiOptions{

	@Autowired
	public Environment environment;

	private IyzicoOptions options;

	public IyzicoOptions() {
		// default empty constructor
	}
	
    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }
	public synchronized IyzicoOptions getOptions() {
		if(options == null) {
			options = new IyzicoOptions();
			options.setApiKey(environment.getProperty("event.management.api.key"));
			options.setSecretKey(environment.getProperty("event.management.secret"));
			options.setBaseUrl(environment.getProperty("event.management.base.url"));
			return options;
		}else{
			return options;
		}
	}
	


}
