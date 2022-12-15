package com.gsix.model.serivce;

import com.gsix.entity.Customer;

public interface ConsumerService {

	public boolean loginCheck(String userName, String userPassword);
	
//	public Customer getCustomerByUserEmail(String userEmail);
	
	public boolean emailCheckExists(String userEmail);
	
	public Customer createProducts(Customer product);
}