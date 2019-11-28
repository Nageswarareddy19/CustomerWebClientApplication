package com.customer.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.customer.client.constants.AppConstants;
import com.customer.client.model.Customer;
import com.customer.client.properties.AppProperties;

import reactor.core.Disposable;

@Service
public class CustomerClientService {
	static Logger logger=LoggerFactory.getLogger(CustomerClientService.class);
	
	public CustomerClientService() {
		logger.debug("CustomerClinetService():instantiated");
		logger.info("CustomerClinetService():instantiated");
		
	}
	
	@Autowired
	private AppProperties props;
	
	
	@Autowired
	private WebClient client;

	public Disposable invokeCustomerById(Integer customerID) {
		logger.debug("invokeCustomerById() method is called");
		
		
		Disposable customer = client.get().uri(props.getMessages().get((AppConstants.GET_CUSTOMER_BY_ID)) + customerID)
				.retrieve()
				.bodyToMono(Customer.class)
				.subscribe(CustomerClientService::doWork);
		logger.debug("invokeCustomerById() method is ended");
		logger.info("invokeCustomerById() method is sucessfully executed");
		return customer;

	}

	public static void doWork(Customer customer) {
		logger.debug("doWork() method is called");
		System.out.println(customer);
		logger.debug("doWork() method is ended");
		logger.info("doWork() method is sucessfully called");
	}

	public ResponseEntity<Customer> invokeCustomerByEmail(String email) {
		logger.debug("invokeCustomerByEmail() method is called");

		Customer customer = client.get().uri(props.getMessages().get(AppConstants.GET_CUSTOMER_BY_EMAIL), email).retrieve().bodyToMono(Customer.class)
				.block();
		logger.debug("invokeCustomerByEmail() method is ended");
		logger.info("invokeCustomerByEmail() method is sucessfully executed");
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}

	public ResponseEntity<String> invokeAllCustomerEmails() {
		
		logger.debug("invokeAllCustomerEmail() method is called");
		String emails = client.get().uri(props.getMessages().get(AppConstants.GET_ALL_CUSTOMER_EMAILS)).retrieve().bodyToMono(String.class).block();
		logger.debug("invokeAllCustomerEmail() method is ended");
		logger.info("invokeAllCustomerEmail() method is sucessfully executed");
		return new ResponseEntity<String>(emails, HttpStatus.OK);

	}

	public ResponseEntity<String> invokeAddCustomerDetails(Customer customer) {
		logger.debug("invokeAllCustomerEmail() method is started");
		
		String response = client.post().uri(props.getMessages().get(AppConstants.ADD_CUSTOMER_DETAILS))
				.body(BodyInserters.fromValue(customer)).retrieve().bodyToMono(String.class).block();
		logger.debug("invokeAllCustomerEmail() method is sucessfully executed");
		logger.info("invokeAllCustomerEmail() method is ended");
		return new ResponseEntity<String>(response, HttpStatus.CREATED);

	}
}