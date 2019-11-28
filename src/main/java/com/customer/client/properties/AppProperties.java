package com.customer.client.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Configuration
@ConfigurationProperties(prefix = "web-client")
public class AppProperties {
	
	
	private Map<String, String> messages=new HashMap<>();
	
	

}
