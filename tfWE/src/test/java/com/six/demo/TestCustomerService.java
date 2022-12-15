package com.six.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.six.entity.Customer;
import com.six.persistence.CustomerDao;
import com.six.service.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class TestCustomerService {

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;
    @Mock
    private CustomerDao customerDao;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() throws Exception {
        autoCloseable=MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    
    // positive
    @Test
    void testLoginCheckPos(){
    	
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
        when(customerDao.findByUserEmailAndUserPassword("t.person@gmail.com", "Password10")).thenReturn(customer);

        assertEquals(customer, customerServiceImpl.loginCheck("t.person@gmail.com", "Password10"));
    }
    
    //negative
    @Test
    void testLoginCheckNeg(){
    	
    	//Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
    	when(customerDao.findByUserEmailAndUserPassword("t.person@gmail.com", "Password10")).thenReturn(null);
    	
    	assertNull(customerServiceImpl.loginCheck("t.person@gmail.com", "Password10"));
    			
    }

    // positive
    @Test
    void testAddCustomerPos() throws SQLIntegrityConstraintViolationException{
    	
    	Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);

    	when(customerDao.findByUserEmailAndUserPassword("t.person@gmail.com", "Password10")).thenReturn(null);
    	assertEquals(customer, customerServiceImpl.addCustomer(customer));
    }
//
    //negative
    @Test
    void testAddCustomerNeg() throws SQLIntegrityConstraintViolationException{
    	
    	Customer customer = new Customer(11, "Test Two", "Password11", "Address11", "t.two@gmail.com", "07877878763", 90.0);
//
    	when(customerDao.findByUserEmailAndUserPassword("t.two@gmail.com", "Password11")).thenReturn(customer);
    	assertEquals(null, customerServiceImpl.addCustomer(customer));
    }
    
    // negative
    @Test
    void testBalanceCheckNeg() {

        Customer customer = new Customer(11, "Test Two", "Password11", "Address11", "t.two@gmail.com", "07877878763", 15.0);

        when(customerDao.findById(11)).thenReturn(Optional.ofNullable(customer));
        assertFalse(customerServiceImpl.balanceCheck(11));
    }

    // positive 
   @Test
   void testBalanceCheckPos() {

       Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);

       when(customerDao.findById(10)).thenReturn(Optional.ofNullable(customer));
       assertTrue(customerServiceImpl.balanceCheck(10));
    }
   
   // positive
   @Test
   void testUpdateBalancePos() {
	   
	   Customer customer = new Customer(11, "Test Two", "Password11", "Address11", "t.two@gmail.com", "07877878763", 100.0);
	   
	   when(customerDao.findById(11)).thenReturn(Optional.ofNullable(customer));
	   assertEquals(customer, customerServiceImpl.updateBalance(11, 50));
   }
   
   // negative
   @Test
   void testUpdateBalanceNeg() {
	   
	   //Customer customer = new Customer(10, "Test Person", "Password10", "Address10", "t.person@gmail.com", "07877878761", 100.0);
	   
	   when(customerDao.findById(11)).thenReturn(Optional.ofNullable(null));
	   assertEquals(null, customerServiceImpl.updateBalance(10, 50));
   }

}