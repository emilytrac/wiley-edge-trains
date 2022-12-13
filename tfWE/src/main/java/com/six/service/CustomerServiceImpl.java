package com.six.service;

import com.six.entity.Customer;
import com.six.persistence.CustomerDao;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

//    @Override
//    public Collection<Customer> getAllCustomers() {
//        return customerDao.findAll();
//    }


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
    
    @Override
    public Customer addCustomer(Customer customer) {
	     
    	if (customerDao.findByUserEmailAndUserPassword(customer.getUserName(), customer.getUserPassword()) == null) {
    		
    		customerDao.save(customer);
    		return customer;
    	} else {
    		return null;
    	}
    }

	@Override
	public boolean balanceCheck(int userId) {
		Customer customer = customerDao.findById(userId).orElse(null);
		if(customer.getCardBalance() >= 20) 
			return true;
		else
			return false;
	}

	@Override
	public boolean updateBalance(int userId, double inc) {
		Customer customer = customerDao.findById(userId).orElse(null);
		if (customer != null) {
			customer.setCardBalance(customer.getCardBalance() + inc);
			customerDao.save(customer);
			return true;
		} else {
			return false;
		}
	}
}