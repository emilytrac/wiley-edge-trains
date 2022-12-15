package com.gsix.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gsix.entity.Transaction;

@Repository
public interface ConsumerDao extends JpaRepository<Transaction, Integer> {
	
	//return a list of transactions that a particular user has made
	public List<Transaction> searchTransactionByUserId(int userId);

}
