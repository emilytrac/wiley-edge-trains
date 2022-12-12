package com.six.service;

import com.six.entity.Customer;
import java.util.Collection;
import java.util.List;

public interface CustomerService {

	Collection<Customer> getAllCustomers();
        
        public Customer findByUserEmail(String userEmail);
        
        boolean addCustomer(Customer customer);
        
}
