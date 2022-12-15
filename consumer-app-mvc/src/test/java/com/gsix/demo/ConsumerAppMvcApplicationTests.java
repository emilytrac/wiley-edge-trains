package com.gsix.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;

import com.gsix.entity.Customer;
import com.gsix.entity.Transaction;
import com.gsix.entity.TransactionList;
import com.gsix.model.persistence.ConsumerDao;
import com.gsix.model.serivce.ConsumerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class ConsumerAppMvcApplicationTests {

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
    
    // Save Transaction test 
    
    @Test 
    void saveTransactionTest() {
    	
    	Transaction transaction = new Transaction(1, 1, LocalDateTime.now(), "Waterloo", LocalDateTime.now(), "Paddington", 5);
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	Customer customerTwo = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 95.0);
    	
    	//consumerServiceImpl.updateBalance(10, -transaction.getFareCost());
    	
    	HttpHeaders headers = new HttpHeaders();
        
	    HttpEntity<Customer> entity = new HttpEntity<Customer>(headers);
    	
	    
	   // when(consumerServiceImpl.updateBalance(customer.getUserId(), -transaction.getFareCost())).thenReturn(customerTwo);
	    when(restTemplate.exchange("http://localhost:8082/customers/" + 10 + "/" + 5, HttpMethod.PUT, entity, Customer.class).getBody()).thenReturn(customerTwo);
	    when(consumerDao.save(transaction)).thenReturn(transaction);
	    assertEquals(String.valueOf(95), (String.valueOf(consumerServiceImpl.updateBalance(10, 5.00).getCardBalance())));
	    
    }
    
    // Transaction history test
    
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
    
    // Transaction history test
    
    @Test
    void testShowTransactionHistoryNeg() {
    	
    	TransactionList transactionList = new TransactionList();
    	
    	when(consumerDao.searchTransactionByUserId(1)).thenReturn(null);
    	assertEquals(transactionList, consumerServiceImpl.showTransactionHistory(1));
    	
    }
  
}