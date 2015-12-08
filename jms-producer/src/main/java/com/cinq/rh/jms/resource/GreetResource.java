package com.cinq.rh.jms.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.cinq.rh.jms.Client;

@Path("/greet")
@Component
public class GreetResource {
	static Logger logger = LoggerFactory.getLogger(GreetResource.class);

	@Autowired
	Client client;

	public GreetResource() {
		logger.info("Resource Initialized");
	}

	@GET
	@Path("/echo/{msg}")
	@Produces({ MediaType.TEXT_PLAIN_VALUE })
	public Response echo(@PathParam("msg") String message) {
		return Response.ok().entity(message).build();
	}

	@POST
	@Path("/send")
	@Consumes({ MediaType.TEXT_PLAIN_VALUE })
	public boolean sendMessage(String greeting) {

		client.sendAMessage(greeting);
		return true;
	}
}
