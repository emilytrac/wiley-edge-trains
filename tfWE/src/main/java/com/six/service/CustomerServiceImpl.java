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

    @Override
    public Collection<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

 


    @Override
    public boolean addCustomer(Customer customer) {
    try{
        customerDao.insertCustomer(customer.getUserId(),customer.getUserName(),customer.getUserPassword(), customer.getUserAddress(),
                customer.getUserEmail(), customer.getUserPhone(), customer.getCardBalance());
        return true;
    }
    
    
    catch(SQLIntegrityConstraintViolationException ex) {
        return false;
    }
    catch(Exception ex) {
        return false;
    }
  }




	@Override
	public Customer findByUserEmail(String userEmail) {
		return customerDao.findByUserEmail(userEmail);
	}
}