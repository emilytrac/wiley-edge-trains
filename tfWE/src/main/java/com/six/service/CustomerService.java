package com.six.service;

import com.six.entity.Customer;

public interface CustomerService {

	// Collection<Customer> getAllCustomers();
        
    Customer loginCheck(String userEmail, String userPassword);
        
    Customer addCustomer(Customer customer);
    
    boolean balanceCheck(int userId);
    
    Customer updateBalance(int userId, double inc);
        
}
