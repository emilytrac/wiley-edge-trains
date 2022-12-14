package com.gsix.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

	@Id
	private int transactionId;
	private int userId;
	private LocalDateTime swipeIn;
	private String swipeInStationName;
	private LocalDateTime swipeOut;
	private String swipeOutStationName;
	private double fareCost;
	
}
