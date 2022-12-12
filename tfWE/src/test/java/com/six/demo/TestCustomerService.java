package com.six.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		/*
		 * tells mockito to scan the test class instance 
		 * for all the fields annotated with @Mock
		 * and initialize those fields as mocks
		 * 
		 */
		autoCloseable=MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	void testAddCustomerOne() throws SQLIntegrityConstraintViolationException{
		when(customerDao.insertCustomer(5, "Joe Doe", "Password5", "Address5", "joe.doe@gmail.com", "07877878762", 100.0)).thenReturn(1);
		
		assertTrue(customerServiceImpl.addCustomer(new Customer(5, "Joe Doe", "Password5", "Address5", "joe.doe@gmail.com", "07877878762", 100.0)));
	}
	
	@Test
	void testAddCustomerTwo() throws SQLIntegrityConstraintViolationException{
		when(customerDao.insertCustomer(5, "Joe Doe", "Password5", "Address5", "joe.doe@gmail.com", "07877878762", 100.0)).thenThrow(SQLIntegrityConstraintViolationException.class) ;
		
		assertFalse(customerServiceImpl.addCustomer(new Customer(5, "Joe Doe", "Password5", "Address5", "joe.doe@gmail.com", "07877878762", 100.0)));
	}
	
	@Test
	void testFindCustomerByID(){
		when(customerDao.findByUserEmail("j.doe@gmail.com")).thenReturn(new Customer(1, "John Doe", "Password1", "Address1", "j.doe@gmail.com", "07877878761", 100.0));
		
		Customer customer = new Customer(1, "John Doe", "Password1", "Address1", "j.doe@gmail.com", "07877878761", 100.0);
		assertEquals(customer, customerServiceImpl.findByUserEmail("j.doe@gmail.com"));
	}

}
