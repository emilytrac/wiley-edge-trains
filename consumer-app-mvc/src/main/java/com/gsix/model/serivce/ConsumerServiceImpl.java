package com.gsix.model.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
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

	// calling REST API to get customer object for login - then used to set session
	@Override
	public Customer loginCheck(String userEmail, String userPassword) {
		
		Customer customer = restTemplate.getForObject("http://localhost:8082/customers/" + userEmail + "/" + userPassword, Customer.class);
		
		return customer;
	}
	
	// calling REST API to add a new customer following collection of info needed
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
			return customer;
		} else {
			return null;
		}
	}

	// calling REST API to check customer balance
	@Override
	public String balanceCheck(int userId) {
		
		String balance = restTemplate.getForObject("http://localhost:8082/customers/" + userId, String.class);
		
		return balance;
	}

	// calling REST API to update the balance
	@Override
	public Customer updateBalance(int userId, double inc) {
		
		 HttpHeaders headers = new HttpHeaders();
	        
	     HttpEntity<Customer> entity = new HttpEntity<Customer>(headers);
	        
	     Customer customer = restTemplate.exchange("http://localhost:8082/customers/" + userId + "/" + inc, HttpMethod.PUT, entity, Customer.class).getBody();
	        
	     return customer;
	}

	// calling REST API to check the cost of the route
	@Override
	public double checkRoute(String sourceStation, String destinationStation) {
		
		String routeCost = restTemplate.getForObject("http://localhost:8084/stations/" + sourceStation + "/" + destinationStation, String.class);
		
		return Double.parseDouble(routeCost);
	}

	// saving the transaction and updating the customer balance based on the fare price
	@Override
	public Transaction saveTransactionAndUpdateBalance(Transaction transaction, int userId) {
		
		updateBalance(userId, -transaction.getFareCost());
		
		consumerDao.save(transaction);
		
		return transaction;
	}

	// calling REST API to get station object for swipe in/swipe out
	@Override
	public Station getStationByStationName(String stationName) {
		
		Station station = restTemplate.getForObject("http://localhost:8084/stations/" + stationName, Station.class);
		
		return station;
	}

	// calling REST API to list all the stations
	@Override
	public StationList showAllStations() {
		
		StationList stations = restTemplate.getForObject("http://localhost:8084/stations/", StationList.class);
		
		return stations;
	}

	// creating a list of transaction objects carried out by a user
	@Override
	public TransactionList showTransactionHistory(int userId) {
		
		TransactionList transactionList = new TransactionList();
		
		transactionList.setTransactions(consumerDao.searchTransactionByUserId(userId));
		
		return transactionList;
	}
	
}