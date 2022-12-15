package com.six.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
        @Column(name="userId")
	private int userId;
        @Column(name="userName")
	private String userName;
        @Column(name="userPassword")
        private String userPassword;
        @Column(name="userAddress")
        private String userAddress;
        @Column(name="userEmail")
        private String userEmail;
        @Column(name="userPhone")
        private String userPhone;
        @Column(name="cardBalance")
        private Double cardBalance; 
}
