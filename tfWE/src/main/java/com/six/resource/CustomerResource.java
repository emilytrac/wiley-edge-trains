package com.six.resource;

import com.six.entity.Customer;
import com.six.entity.CustomerList;
import com.six.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping(path = "/customers/{userEmail}/{userPassword}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer loginCheckResource(@PathVariable("userEmail") String userEmail, @PathVariable("userPassword") String userPassword) {
		return customerService.loginCheck(userEmail, userPassword);
	}
	
	@PostMapping(path = "/customers",produces = MediaType.TEXT_PLAIN_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCustomerResource(@RequestBody Customer customer) {
        if(customerService.addCustomer(customer) != null)
            return "User added";
        else

            return "User not added";
    }
	
	@GetMapping(path = "/customers/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String balanceCheckResource(@PathVariable("userId") int userId) {
		if(customerService.balanceCheck(userId))
			return "Sufficient funds";
		else
			return "Insufficient funds";
	}
	
	@PutMapping(path = "/customers/{userId}/{inc}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateBalanceResource(@PathVariable("userId") int userId, @PathVariable("inc") double inc) {
	     if (customerService.updateBalance(userId, inc))
	         return "Balance successfully updated with" + inc;
	     else
	         return "Something went wrong, please try again.";
	}
}
