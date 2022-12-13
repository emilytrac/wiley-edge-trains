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
import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.entity.Transaction;
import com.gsix.entity.TransactionList;
import com.gsix.model.persistence.ConsumerDao;

@Service
public class ConsumerServiceImpl implements ConsumerService {
	
	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
	public ConsumerDao consumerDao;

	@Override
	public Customer loginCheck(String userEmail, String userPassword) {
		
		Customer customer = restTemplate.getForObject("http://localhost:8082/customers/" + userEmail + "/" + userPassword, Customer.class);
		
//		try {
//			if (customer.getUserEmail().equals(userEmail) && customer.getUserPassword().equals(userPassword)) {
//				return true;
//			}
//		} catch (Exception e) {
//			return false;
//		}
		return customer;
	}
	
	@Override
	public Customer addNewCustomer(String userName, String userPassword, String userAddress, String userEmail,
			String userPhone, double cardBalance) {
		
		Customer customer = new Customer();
		
		customer.setUserName(userName);
		customer.setUserPassword(userPassword);
		customer.setUserAddress(userAddress);
		customer.setUserEmail(userEmail);
		customer.setUserPhone(userPhone);
		customer.setCardBalance(cardBalance);
		
		String message = restTemplate.postForObject("http://localhost:8082/customers/", customer, String.class);
		
		if("User added".equals(message)) {
			return loginCheck(userName, userPassword);
		} else {
			return null;
		}
	}

//	public Customer createProducts(@RequestBody Customer product) {
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	    HttpEntity<Customer> entity = new HttpEntity<Customer>(product,headers);
//
//	    ResponseEntity<Customer> response = restTemplate.exchange(
//	            "http://localhost:8082/customers", HttpMethod.POST, entity, Customer.class);
//
//	    return response.getBody();
//	}

	@Override
	public String balanceCheck(int userId) {
		
		String balance = restTemplate.getForObject("http://localhost:8082/customers/" + userId, String.class);
		
		return balance;
	}

	@Override
	public Customer updateBalance(int userId, double inc) {
		
		HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<Customer> entity = new HttpEntity<Customer>(headers);
        
		Customer customer = restTemplate.exchange("http://localhost:8082/customers/" + userId + "/" + inc, HttpMethod.PUT, entity, Customer.class).getBody();
		
		return customer;
	}

	@Override
	public double checkRoute(String sourceStation, String destinationStation) {
		
		double routeCost = restTemplate.getForObject("http://localhost:8084/stations/" + sourceStation + "/" + destinationStation, Double.class);
		
		return routeCost;
	}

	@Override
	public Transaction saveTransaction(Transaction transaction, int userId) {
		
		updateBalance(userId, -transaction.getFareCost());
		
		consumerDao.save(transaction);
		
		return transaction;
	}

	@Override
	public TransactionList showTransactionHistory(int userId) {
		
		TransactionList transactionList = new TransactionList();
				
		transactionList.setTransactions(consumerDao.searchTransactionByUserId(userId));
		
		return transactionList;
	}

	@Override
	public Station getStationByStationName(String stationName) {
		
		Station station = restTemplate.getForObject("http://localhost:8084/stations/" + stationName, Station.class);
		
		return station;
	}

	@Override
	public StationList showAllStations() {
		
		StationList stations = restTemplate.getForObject("http://localhost:8084/stations/", StationList.class);
		
		return stations;
	}
	

}