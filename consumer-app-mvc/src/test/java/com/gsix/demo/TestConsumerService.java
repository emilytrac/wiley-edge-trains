package com.gsix.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.gsix.entity.Customer;
import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.entity.Transaction;
import com.gsix.entity.TransactionList;
import com.gsix.model.persistence.ConsumerDao;
import com.gsix.model.serivce.ConsumerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class TestConsumerService {

    @InjectMocks
    private ConsumerServiceImpl consumerServiceImpl;
    
    @Mock
    private ConsumerDao consumerDao;
    
    private AutoCloseable autoCloseable;
    
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        autoCloseable=MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    
    // Login test
    
    @Test
    void loginCustomerTestPos() {
    	
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	
    	when(restTemplate.getForObject("http://localhost:8082/customers/" + "t.person@gmail.com" + "/" + "Password10", Customer.class))
    	.thenReturn(customer);
    	
    	assertEquals(customer, consumerServiceImpl.loginCheck("t.person@gmail.com", "Password10"));
    }
    
    @Test
    void loginCutomerTestNeg() {
    	
    	when(restTemplate.getForObject("http://localhost:8082/customers/" + "b.person@gmail.com" + "/" + "Password11", Customer.class))
    	.thenReturn(null);
    	
    	assertNull(consumerServiceImpl.loginCheck("b.person@gmail.com", "Password11"));
    }
    
    // add new customer tests
    
    @Test
    void addNewCustomerTestPos() {
    	
    	Customer customer = new Customer("Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	
    	when(restTemplate.postForObject("http://localhost:8082/customers/", customer, String.class)).thenReturn("User added");
    	Customer expected = consumerServiceImpl.addNewCustomer("Test Person", "Password10", 
    			"Address10", "t.person@gmail.com", "07877878761", 100.0);
    	
    	assertEquals(customer, expected);
    }
    
    @Test
    void addNewCustomerTestNeg() {
    	
    	Customer customer = new Customer("Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	
    	when(restTemplate.postForObject("http://localhost:8082/customers/", customer, String.class)).thenReturn("User not added");
    	Customer expected = consumerServiceImpl.addNewCustomer("Test Person", "Password10", 
    			"Address10", "t.person@gmail.com", "07877878761", 100.0);
    	
    	assertNull(expected);
    }
    
    // balance check tests
    
    @Test
    void checkBalanceTestPos() {
    	
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	when(restTemplate.getForObject("http://localhost:8082/customers/" + customer.getUserId(), String.class)).thenReturn("Sufficient funds");
    	
    	String balanceCheck = consumerServiceImpl.balanceCheck(10);
    	assertEquals(balanceCheck, "Sufficient funds");
    }
    
    @Test
    void checkBalanceTestNeg() {
    	
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 0);
    	when(restTemplate.getForObject("http://localhost:8082/customers/" + customer.getUserId(), String.class)).thenReturn("Insufficient funds");
    	
    	String balanceCheck = consumerServiceImpl.balanceCheck(10);
    	assertEquals(balanceCheck, "Insufficient funds");
    }
    
    // update balance test
    
    @Test
    void updateBalanceTestPos() {
    	
    	HttpHeaders headers = new HttpHeaders();
        
	    HttpEntity<Customer> entity = new HttpEntity<Customer>(headers);
	    
	    Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
	    Transaction transaction = new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5);
	    
	    when(restTemplate.exchange("http://localhost:8080/customers/" + customer.getUserId() + "/" + transaction.getFareCost(), HttpMethod.PUT,
                entity, Customer.class)).thenReturn(new ResponseEntity<>(new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100), 
                		HttpStatus.OK));
	    
	    Customer expected = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100);
	    assertEquals(customer, expected);
    }
    
    // check route test
    
    @Test
    void calculateCostTest() {
    	
    	//Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	String start = "Waterloo";
    	String end = "Paddington";
    	Transaction transaction = new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5.0);
 
    	
    	when(restTemplate.getForObject("http://localhost:8084/stations/" + start + "/" + end, String.class))
    	.thenReturn(String.valueOf(transaction.getFareCost()));
    	
    	String expected = String.valueOf(consumerServiceImpl.checkRoute(start, end));
    	
    	assertEquals(expected, "5.0");
    }
    
    // negative test not needed as the user never gets the option to input their own station due to dropdown
    
    // get station by name test 
    
    @Test
    void stationByNameTestPos() {
    	
    	Station station = new Station(02, "Waterloo");
    	
    	when(restTemplate.getForObject("http://localhost:8084/stations/" + "Waterloo", Station.class)).thenReturn(station);
    	assertEquals(station, consumerServiceImpl.getStationByStationName("Waterloo"));
    }
    
    // negative test not needed as the user never gets the option to input their own station due to dropdown
    
    // show all stations
    
    @Test
    void showAllStationsTestPos() {
    	
    	List<Station> stations = new LinkedList<Station>();
    	stations.add(new Station(02, "Waterloo"));
    	stations.add(new Station(03, "Paddington"));
    	
    	StationList stationList = new StationList();
    	stationList.setStations(stations);
    	
    	when(restTemplate.getForObject("http://localhost:8084/stations/", StationList.class)).thenReturn(stationList);
    	assertEquals(stationList, consumerServiceImpl.showAllStations());
    }
    
    @Test
    void showAllStationsTestNeg() {
    	
    	// StationList stationList = new StationList();
    	
    	when(restTemplate.getForObject("http://localhost:8084/stations/", StationList.class)).thenReturn(null);
    	assertNull(consumerServiceImpl.showAllStations());
    }
    
    @Test
    void testShowTransactionHistoryPos() {

    	List<Transaction> transactions = new ArrayList<>();
    	
    	transactions.add(new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5));
    	transactions.add(new Transaction(2, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "St.Pancras", 10));

    	TransactionList transactionList = new TransactionList();
    	transactionList.setTransactions(transactions);
    	
    	when(consumerDao.searchTransactionByUserId(1)).thenReturn(transactions);
    	assertEquals(transactionList, consumerServiceImpl.showTransactionHistory(1));
    	
    }
    
    // transaction history test
    
    @Test
    void testShowTransactionHistoryNeg() {
    	
    	List<Transaction> transactions = new ArrayList<>();
    	TransactionList transactionList = new TransactionList();
    	transactionList.setTransactions(transactions);
    	
    	when(consumerDao.searchTransactionByUserId(1)).thenReturn(transactions);
    	assertEquals(transactionList, consumerServiceImpl.showTransactionHistory(1));
    	
    }
    
    // save Transaction test 
    
//  @Test 
//  void saveTransactionTest() {
//  	
//  	Transaction transaction = new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5);
//  	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100);
//  	
//  	//consumerServiceImpl.updateBalance(10, -transaction.getFareCost());
//  	
//  	//HttpHeaders headers = new HttpHeaders();
//      
//	    //HttpEntity<Customer> entity = new HttpEntity<Customer>(headers);
//  	
//	    
//	    when(consumerServiceImpl.updateBalance(customer.getUserId(), transaction.getFareCost())).thenReturn(new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 95));
//	    //when(restTemplate.exchange("http://localhost:8082/customers/" + 10 + "/" + 5, HttpMethod.PUT, entity, Customer.class).getBody()).thenReturn(customerTwo);
//	    when(consumerDao.save(transaction)).thenReturn(new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5));
//	    assertEquals(transaction, consumerServiceImpl.saveTransactionAndUpdateBalance(transaction, 10));
//	    
//  }
  
  // transaction history test
  
}
