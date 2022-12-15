package com.gsix.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gsix.entity.Customer;
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
		
		// controllers for logging in
		
		@RequestMapping("/loginPage")
		public ModelAndView loginPageController() {
			return new ModelAndView("loginpage");
		}
		
			@RequestMapping("/login")
			public ModelAndView getMainPageController(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) {
				
				ModelAndView modelAndView=new ModelAndView();
				
				if (consumerService.loginCheck(userEmail, userPassword)) {
					modelAndView.setViewName("homepage");
				} else {
					modelAndView.addObject("message", "Invalid User Credentials, Please try again");
					modelAndView.setViewName("index");
				}
				return modelAndView;
				
			}
	

			// controllers for signing up
			
			@RequestMapping("/registerNew")
			public ModelAndView signUpPageController() {
				return new ModelAndView("signup");
			}
			
			@RequestMapping("/signup")
			public ModelAndView processRegisterController(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,
					@RequestParam("userAddress") String userAddress, @RequestParam("userEmail") String userEmail,
					@RequestParam("userPhone") String userPhone, @RequestParam("cardBalance") Double cardBalance) {
				Customer customer = new Customer(userName, userPassword, userAddress, userEmail, userPhone, cardBalance);
				
				ModelAndView modelAndView=new ModelAndView();
				
				if (consumerService.emailCheckExists(userEmail)) {
		  		    consumerService.createProducts(customer);
					return new ModelAndView("registersuccess");
				} else {
				modelAndView.addObject("message", "Email is already taken! Please try again!");
				modelAndView.setViewName("index");
				}
				return modelAndView;
				
			}
			// controllers for topping up
			
			@RequestMapping("/topUpPage")
			public ModelAndView topUpAccountController() {
				return new ModelAndView("topup");
			}
			
			// controllers for swiping in
			
			@RequestMapping("/swipeIn")
			public ModelAndView swipeInController() {
				return new ModelAndView("swipein");
			}
			
			// controllers for swiping out 
			
			@RequestMapping("/swipeOut")
			public ModelAndView swipeOutController() {
				return new ModelAndView("swipeout");
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
