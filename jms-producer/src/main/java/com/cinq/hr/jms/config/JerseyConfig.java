package com.cinq.hr.jms.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.cinq.hr.jms.resource.GreetResource;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(GreetResource.class);
	}

}
