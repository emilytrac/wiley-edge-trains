package com.gsix.controller;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	// controller to show to first page of an application
	
		@RequestMapping("/")
		public ModelAndView getLoginOrSignUpPageController() {
			return new ModelAndView("index");
		}
		
		// controllers for logging in - working
		
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
				modelAndView.addObject("message", "Invalid User Credentials, Please Try Again");
				modelAndView.setViewName("loginpage");
			}
			return modelAndView;
				
		}
	

		// controllers for signing up - is allowing less than 20 - working fully
			
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
				
			if (customer != null) {
				message = "Account Added, Please Login To Continue.";
//				modelAndView.setViewName("index");
//				modelAndView.setViewName("registersuccess");
			} else {
				message = "Something Went Wrong, This Account May Already Exist Or Funds May Be Insufficient.";
//				modelAndView.setViewName("index");
			}
			
			modelAndView.addObject("message", message);
			modelAndView.setViewName("index");
			return modelAndView;
				
		}
		
		// controllers for topping up - balance is not updating
			
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
			
			if (inc < 0) {
				message = "Top-up Failed, Amount Entered Negative";
			} else {
				if(consumerService.updateBalance(userId, inc) != null) {
					message = "Your Balance Has Now Been Increased By " + inc;
					customer.setCardBalance(customer.getCardBalance() + inc);
				} else {
					message = "Top-up Failed, Please Try Again";
				}
			}
			
			session.setAttribute("user", customer);
			modelAndView.addObject("message", message);
			modelAndView.setViewName("output");
				
			return modelAndView;
		}
			
		// controllers for swiping in - working!
			
		@RequestMapping("/swipeIn")
		public ModelAndView swipeInController() {
				
			// allowing for the user to have a drop-down list to choose from
			ModelAndView modelAndView = new ModelAndView();
			StationList stations = consumerService.showAllStations();
			List<Station> listStations = stations.getStations();
				
			modelAndView.addObject("listStations", listStations);
			modelAndView.setViewName("swipein");
				
			return modelAndView;
			//return new ModelAndView("swipein");
		}
			
		@RequestMapping("/swipedIn")
		public ModelAndView swipedInController(@RequestParam("stationName") String stationName, HttpSession session) {
				
			Transaction transaction = new Transaction();
			ModelAndView modelAndView = new ModelAndView();
			LocalDateTime dateTime = LocalDateTime.now();
			Customer customer = (Customer)session.getAttribute("user");
			String message = null;
				
			if (consumerService.balanceCheck(customer.getUserId()).equals("Insufficient funds")) {
				message = "You Do Not Have Enough Money. Please Top-up";
					
			} else {
				String startStation = stationName;
					
				transaction.setUserId(customer.getUserId());
				transaction.setSwipeIn(dateTime);
				transaction.setSwipeInStationName(stationName);
					
				message = "You Have Swiped In At " + startStation;
			}
				
			// output with message page needed
			modelAndView.addObject("message", message);
			session.setAttribute("transaction", transaction);
			modelAndView.setViewName("output");
				
			return modelAndView;
				
		}
			
		// controllers for swiping out - fully working
			
		@RequestMapping("/swipeOut")
		public ModelAndView swipeOutController() {
			
			// allowing for the user to have a drop-down list to choose from
			ModelAndView modelAndView = new ModelAndView();
			StationList stations = consumerService.showAllStations();
			List<Station> listStations = stations.getStations();
				
			modelAndView.addObject("listStations", listStations);
			modelAndView.setViewName("swipeout");
				
			return modelAndView;
			//return new ModelAndView("swipeout");
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
				
			Double fare = consumerService.checkRoute(transaction.getSwipeInStationName(), endStation);
				
			transaction.setFareCost(fare);
				
			consumerService.saveTransactionAndUpdateBalance(transaction, customer.getUserId());
				
			message = "You Have Swiped Out At " + endStation + " ,Your Remaining Balance Is " + (customer.getCardBalance()-fare);
				
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
