package com.six.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name="Customer")
public class Customer {

	@Id
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
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.cardBalance = cardBalance;
	} 
    
    
}
