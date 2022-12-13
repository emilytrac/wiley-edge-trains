package com.gsix.controller;

import javax.servlet.http.HttpSession;
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
			public ModelAndView getMainPageController(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword, HttpSession session) {
				
				ModelAndView modelAndView=new ModelAndView();
				Customer customer = consumerService.loginCheck(userEmail, userPassword);
				
				// successful login
				if (customer != null) {
					modelAndView.setViewName("homepage");
					modelAndView.addObject("user", customer);
					session.setAttribute("user", customer);
				// login fails
				} else {
					modelAndView.addObject("message", "Invalid User Credentials, Please try again");
					modelAndView.setViewName("loginpage");
				}
				return modelAndView;
				
			}
	

			// controllers for signing up
			
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
				
				if (customer != null) {
					modelAndView.setViewName("registersuccess");
				} else {
				modelAndView.addObject("message", "Something went wrong! Please try again!");
				modelAndView.setViewName("index");
				}
				return modelAndView;
				
			}
			// controllers for topping up
			
			@RequestMapping("/topUpPage")
			public ModelAndView topUpAccountController() {
				return new ModelAndView("topup");
			}
			
			@RequestMapping("/successfulTopUp")
			public ModelAndView topUpPageController(@RequestParam("inc") double inc, HttpServletRequest request, HttpSession session) {
				
				ModelAndView modelAndView = new ModelAndView();
				Customer customer = (Customer)session.getAttribute("user");
				String message = null;
				
				if(consumerService.updateBalance(customer.getUserId(), inc) != null) {
					message = "Your balance has now been increased by " + inc;
				} else {
					message = "top-up failed, please try again";
				}
				
				session.setAttribute("user", customer);
				modelAndView.addObject("message", message);
				modelAndView.setViewName("topupoutput");
				
				return modelAndView;
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
