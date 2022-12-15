package com.gsix.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="Customer")
public class Customer {

        private String userName;
        private String userPassword;
        private String userAddress;
        private String userEmail;
        private String userPhone;
        private Double cardBalance; 
}
