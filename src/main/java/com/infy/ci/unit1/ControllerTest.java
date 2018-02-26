package com.infy.ci.unit1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Configuration
@PropertySource("classpath:/application.properties")
@RequestMapping("/unittestdata")
public class ControllerTest {
	
	 Environment env;
	   private String requestQueueName = "rpc_queue1";
	   
	   @Value("${spring.rabbitmq.host}")
	    private String rabHost;
	    
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	            return new PropertySourcesPlaceholderConfigurer();
	    }

	    @Autowired
	    RabbitTemplate rabbitTemplate;
	    
	    @RequestMapping(value="/{projectid}/ut/aggregate",   
	            method = RequestMethod.GET,
	            produces = MediaType.TEXT_HTML_VALUE)    
	    public @ResponseBody String getAggregatedDataForSectionOfNightlyBuild (@PathVariable("projectid") int projectid,
				@RequestParam("buildtype") String buildtype,
				@RequestParam("build") String build) throws Exception {
			
			buildtype = "nightly";
			
			
			if(build.toLowerCase().equals("latest") && buildtype.equals("nightly")){
			    String message = String.format("aggregate");

	             return rabbitTemplate.convertSendAndReceive("", requestQueueName, message).toString();
			}
			else
			{
				return null;
			}
			
	    }
				
}
