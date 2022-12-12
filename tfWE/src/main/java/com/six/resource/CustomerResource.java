package com.six.resource;

import com.six.entity.Customer;
import com.six.entity.CustomerList;
import com.six.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerResource {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping(path="/customers/{userEmail}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer findByUserEmail(@PathVariable("userEmail") String userEmail) {
		return customerService.findByUserEmail(userEmail);
	}
	@PostMapping(path="/customers",produces = MediaType.TEXT_PLAIN_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCustomerResource(@RequestBody Customer customer) {
        if( customerService.addCustomer(customer))
            return "Record added";
        else

            return "Record not added";
    }
}
