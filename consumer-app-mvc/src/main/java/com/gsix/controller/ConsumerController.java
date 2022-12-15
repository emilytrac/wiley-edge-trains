package com.gsix.controller;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gsix.entity.Customer;
import com.gsix.entity.Station;
import com.gsix.entity.StationList;
import com.gsix.entity.Transaction;
import com.gsix.model.serivce.ConsumerService;

@Controller
public class ConsumerController {
	
	@Autowired
	private ConsumerService consumerService;

		// controller to show to show the login/sign up page of the application
	
		@RequestMapping("/")
		public ModelAndView getLoginOrSignUpPageController() {
			return new ModelAndView("index");
		}
		
		// controllers for logging in
		
		@RequestMapping("/loginPage")
		public ModelAndView loginPageController() {
			return new ModelAndView("loginpage");
		}
		
		@RequestMapping("/login")
		public ModelAndView getMainPageController(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword, HttpSession session) {
				
			ModelAndView modelAndView=new ModelAndView();
			Customer customer = consumerService.loginCheck(userEmail, userPassword);
				
			// successful login
			if (customer != null) {
				modelAndView.addObject("user", customer);
				session.setAttribute("user", customer);
				modelAndView.setViewName("homepage");
			// login fails
			} else {
				modelAndView.addObject("message", "Invalid user credentials, please try again!");
				modelAndView.setViewName("loginpage");
			}
			return modelAndView;
				
		}
	

		// controllers for signing up to the application
			
		@RequestMapping("/registerNew")
		public ModelAndView signUpPageController() {
			return new ModelAndView("signup");
		}
			
		@RequestMapping("/registerProcess")
		public ModelAndView processRegisterController(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,
				@RequestParam("userAddress") String userAddress, @RequestParam("userEmail") String userEmail,
				@RequestParam("userPhone") String userPhone, @RequestParam("cardBalance") Double cardBalance, HttpSession session) {
				
			ModelAndView modelAndView=new ModelAndView();
			Customer customer = consumerService.addNewCustomer(userName, userPassword, userAddress, userEmail, userPhone, cardBalance);
			String message;
				
			// successful
			if (customer != null) {
				message = "Account added, please login to continue.";
			// unsuccessful	
			} else {
				message = "Something went wrong, this account may already exist or sign up funds may be insufficient.";
			}
			
			modelAndView.addObject("message", message);
			modelAndView.setViewName("index");
			return modelAndView;
				
		}
		
		// controllers for topping up the balance
			
		@RequestMapping("/topUpPage")
		public ModelAndView topUpAccountController() {
			return new ModelAndView("topup");
		}
			
		@RequestMapping("/successfulTopUp")
		public ModelAndView topUpPageController(@RequestParam("inc") double inc, HttpServletRequest request, HttpSession session) {
				
			ModelAndView modelAndView = new ModelAndView();
			Customer customer = (Customer)session.getAttribute("user");
			String message = null;
			int userId = customer.getUserId();
			
			// ensuring only amount greater than 0 can be entered
			if (inc <= 0) {
				message = "Top-up failed, amount entered negative";
			} else {
				// successful
				if(consumerService.updateBalance(userId, inc) != null) {
					message = "Your balance has now been increased by ₹" + inc;
					customer.setCardBalance(customer.getCardBalance() + inc);
				// unsuccessful
				} else {
					message = "Top-up failed, please try again!";
				}
			}
			
			session.setAttribute("user", customer);
			modelAndView.addObject("message", message);
			modelAndView.setViewName("output");
				
			return modelAndView;
		}
			
		// controllers for swiping in
			
		@RequestMapping("/swipeIn")
		public ModelAndView swipeInController() {
				
			// allowing for the user to have a drop-down list to choose from
			ModelAndView modelAndView = new ModelAndView();
			StationList stations = consumerService.showAllStations();
			List<Station> listStations = stations.getStations();
				
			modelAndView.addObject("listStations", listStations);
			modelAndView.setViewName("swipein");
				
			return modelAndView;
		}
			
		@RequestMapping("/swipedIn")
		public ModelAndView swipedInController(@RequestParam("stationName") String stationName, HttpSession session) {
				
			Transaction transaction = new Transaction();
			ModelAndView modelAndView = new ModelAndView();
			LocalDateTime dateTime = LocalDateTime.now();
			Customer customer = (Customer)session.getAttribute("user");
			String message = null;
				
			// checking that the customer has enough money
			if (consumerService.balanceCheck(customer.getUserId()).equals("Insufficient funds")) {
				message = "You do not have enough money. Please Top-up";
					
			// begin filling in transaction object
			} else {
				String startStation = stationName;
					
				transaction.setUserId(customer.getUserId());
				transaction.setSwipeIn(dateTime);
				transaction.setSwipeInStationName(stationName);
					
				message = "You have swiped in at " + startStation;
			}
				
			modelAndView.addObject("message", message);
			// create session for transaction to continue adding to the object
			session.setAttribute("transaction", transaction);
			modelAndView.setViewName("output");
				
			return modelAndView;
				
		}
			
		// controllers for swiping out
			
		@RequestMapping("/swipeOut")
		public ModelAndView swipeOutController() {
			
			// allowing for the user to have a drop-down list to choose from
			ModelAndView modelAndView = new ModelAndView();
			StationList stations = consumerService.showAllStations();
			List<Station> listStations = stations.getStations();
				
			modelAndView.addObject("listStations", listStations);
			modelAndView.setViewName("swipeout");
				
			return modelAndView;
		}
			
		@RequestMapping("/swipedOut")
		public ModelAndView swipedOutController(@RequestParam("stationName") String stationName, HttpSession session) {
				
			ModelAndView modelAndView = new ModelAndView();
			Transaction transaction = (Transaction)session.getAttribute("transaction");
			LocalDateTime dateTime = LocalDateTime.now();
			Customer customer = (Customer)session.getAttribute("user");
			String message = null;
				
			String endStation = stationName;
				
			transaction.setSwipeOutStationName(stationName);
			transaction.setSwipeOut(dateTime);
				
			// getting the cost of the journey based on swipe out station
			Double fare = consumerService.checkRoute(transaction.getSwipeInStationName(), endStation);
				
			transaction.setFareCost(fare);
				
			// saving the transaction object to the database and updating the balance in the customer table
			consumerService.saveTransactionAndUpdateBalance(transaction, customer.getUserId());
				
			message = "You have swiped out at " + endStation + ", your remaining balance is ₹ " + (customer.getCardBalance()-fare);
				
			modelAndView.addObject("message", message);
			customer.setCardBalance(customer.getCardBalance() - fare);
			session.setAttribute("user", customer);
			modelAndView.setViewName("output");
			return modelAndView;
		}
		
		// controller for showing transactions by user id
		
		@RequestMapping("/showTransactions")
		public ModelAndView showAllCustomerTransactions(HttpSession session) {
			
			ModelAndView modelAndView = new ModelAndView();
			Customer customer = (Customer)session.getAttribute("user");
			
			// getting all of the transactions that the customer has made into a list
			List<Transaction> transactions = consumerService.showTransactionHistory(customer.getUserId()).getTransactions();
			modelAndView.addObject("transactions", transactions);
			modelAndView.setViewName("alltransactions");
			
			return modelAndView;
			
		}
			
		// return to home page
			
		@RequestMapping("/back")
		public ModelAndView returnToMainPageController() {
			return new ModelAndView("homepage");
		}
			
		// return to login/sign up page
			
		@RequestMapping("/logout")
		public ModelAndView logoutController() {
			return new ModelAndView("index");
		}
	
}
