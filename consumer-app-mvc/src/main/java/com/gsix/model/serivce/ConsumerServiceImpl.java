package com.gsix.model.serivce;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.gsix.entity.Customer;

@Service
public class ConsumerServiceImpl implements ConsumerService {
	
	@Autowired
	public RestTemplate restTemplate;

	@Override
	public boolean loginCheck(String userEmail, String userPassword) {
		
		Customer customer = restTemplate.getForObject("http://localhost:8082/customers/{userEmail}", Customer.class, userEmail);
		
		try {
			if (customer.getUserEmail().equals(userEmail) && customer.getUserPassword().equals(userPassword)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

//	@Override
//	public Customer getCustomerByUserEmail(String userEmail) {
//		
//		Customer customer = restTemplate.getForObject("http://localhost:8082/customers"+userEmail, Customer.class);
//		
//		
//	}

	
	
	@Override
	public boolean emailCheckExists(String userEmail) {
       Customer customer = restTemplate.getForObject("http://localhost:8082/customers/:userEmail"+userEmail, Customer.class);
		
		try {
			if (customer.getUserEmail().equals(userEmail)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}
	
	@PostMapping(value = "/customers", consumes = "application/json")
	public Customer createProducts(@RequestBody Customer product) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Customer> entity = new HttpEntity<Customer>(product,headers);

	    ResponseEntity<Customer> response = restTemplate.exchange(
	            "http://localhost:8082/customers", HttpMethod.POST, entity, Customer.class);

	    return response.getBody();
	}
	

}