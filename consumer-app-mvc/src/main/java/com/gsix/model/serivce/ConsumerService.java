package com.gsix.model.serivce;

import com.gsix.entity.Customer;
import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.entity.Transaction;

public interface ConsumerService {

	public Customer loginCheck(String userName, String userPassword);
	
//	public Customer getCustomerByUserEmail(String userEmail);
	
	public Customer addNewCustomer(String userName, String userPassword, String userAddress, String userEmail, String userPhone,
			double cardBalance);
	
    public String balanceCheck(int userId);
    
    public Customer updateBalance(int userId, double inc);
    
    // price of route
    public double checkRoute(String sourceStation, String destinationStation);
    
    // save transaction
    public Transaction saveTransactionAndUpdateBalance(Transaction transaction, int userId);
    
//  public TransactionList showTransactionHistory(int userId);
    
    public Station getStationByStationName(String stationName);
    
    public StationList showAllStations();
}