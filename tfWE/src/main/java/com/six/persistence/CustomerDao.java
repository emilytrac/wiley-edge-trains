package com.six.persistence;

import com.six.entity.Customer;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    
    public Customer findByUserEmailAndUserPassword(String userEmail, String userPassword);
  
    @Modifying
    @Transactional
    @Query(value = "insert into customer values(:userId,:userName,:password,:userAddress,:userEmail,:userPhone,:cardBalance)", nativeQuery = true)
    int insertCustomer(@Param("userId") int userId, @Param("userName") String userName, @Param("password") String password,
            @Param("userAddress") String userAddress,@Param("userEmail") String userEmail,@Param("userPhone") String userPhone, @Param("cardBalance") double cardBalance)throws SQLIntegrityConstraintViolationException;

}