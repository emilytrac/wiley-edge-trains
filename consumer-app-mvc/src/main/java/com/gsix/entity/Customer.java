package com.gsix.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="Customer")
public class Customer {

	private int userId;
	private String userName;
    private String userPassword;
    private String userAddress;
    private String userEmail;
    private String userPhone;
    private double cardBalance;
    
    // all but id for auto increment
	public Customer(String userName, String userPassword, String userAddress, String userEmail, String userPhone,
			double cardBalance) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.cardBalance = cardBalance;
	} 
}
