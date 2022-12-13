package com.six.service;

import com.six.entity.Customer;
import java.util.Collection;
import java.util.List;

public interface CustomerService {

	// Collection<Customer> getAllCustomers();
        
    Customer loginCheck(String userEmail, String userPassword);
        
    Customer addCustomer(Customer customer);
    
    boolean balanceCheck(int userId);
    
    boolean updateBalance(int userId, double inc);
        
}
