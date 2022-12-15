package com.six.service;

import com.six.entity.Customer;
import com.six.persistence.CustomerDao;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    // logging in if the customer exists in the database
    @Override
	public Customer loginCheck(String userEmail, String userPassword) {
		try {
			Customer customer = customerDao.findByUserEmailAndUserPassword(userEmail, userPassword);
			if (customer != null)
				return customer;
			return null;
		} catch (Exception e) {
			return null;
		}
	}
    
    // adding a customer if they don't already exist, email is unique so cannot enter if duplicate
    @Override
    public Customer addCustomer(Customer customer) throws SQLIntegrityConstraintViolationException {
	    try { 
	    	if (customerDao.findByUserEmailAndUserPassword(customer.getUserEmail(), customer.getUserPassword()) == null 
	    			&& customer.getCardBalance() >=100) {
	    		customerDao.save(customer);
	    		return customer;
	    	} else {
	    		return null;
	    	} 
	    } catch (Exception e) {
			return null;
		}
    }

    // checking that the customer has sufficient balance to swipe into the application
	@Override
	public boolean balanceCheck(int userId) {
		Customer customer = customerDao.findById(userId).orElse(null);
		if(customer.getCardBalance() >= 20) 
			return true;
		else
			return false;
	}

	// updating the customer balance after the customer swipes out
	@Override
	public Customer updateBalance(int userId, double inc) {
		Customer customer = customerDao.findById(userId).orElse(null);
		if (customer != null) {
			customer.setCardBalance(customer.getCardBalance() + inc);
			customerDao.save(customer);
			return customer;
		} else {
			return null;
		}
	}

}