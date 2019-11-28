package com.customer.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;

import com.customer.client.model.Customer;
import com.customer.client.service.CustomerClientService;

import reactor.core.Disposable;

@SpringBootApplication
public class Proj08CustomerWebClienttExampleApplication {
	
	static Logger logger=LoggerFactory.getLogger(Proj08CustomerWebClienttExampleApplication.class);
	public Proj08CustomerWebClienttExampleApplication() {

		logger.debug("CustomerWebClienttExampleApplication::Instantiated");
		logger.info("CustomerWebClienttExampleApplication::Instantiated");
		
	}

	public static void main(String[] args) {
		logger.debug("main() method is called");
		
		ConfigurableApplicationContext context = SpringApplication.run(Proj08CustomerWebClienttExampleApplication.class,
				args);
		CustomerClientService service = context.getBean(CustomerClientService.class);
		Disposable customer = service.invokeCustomerById(23);
		System.out.println(customer.toString());

		ResponseEntity<Customer> entity = service.invokeCustomerByEmail("naveen@gmail.com");

		Customer customerData = entity.getBody();
		System.out.println(customerData);
		ResponseEntity<String> emails = service.invokeAllCustomerEmails();
		System.out.println(emails.getBody());

		Customer c = new Customer();
		c.setCustomerName("praveenreddy");
		c.setCustomerEmail("praveen@gmail.com");
		ResponseEntity<String> response = service.invokeAddCustomerDetails(c);
		System.out.println(response);
		logger.debug("main() method is sucessfully executed");

	}

}
