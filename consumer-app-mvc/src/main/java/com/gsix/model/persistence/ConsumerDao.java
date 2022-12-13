package com.gsix.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gsix.entity.Transaction;

@Repository
public interface ConsumerDao extends JpaRepository<Transaction, Integer> {
	
//	public List<Transaction> searchTransactionByUserId(int userId);
//	
//	public Transaction searchTransactionByUserIdAndSwipeOut(int userId, LocalDateTime swipeOut);

}
