package com.report.inventory.api.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.report.inventory.service.CustomerOrderService;
import com.report.inventory.utility.InputValidation;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryManagementController {
	
	@Autowired private InputValidation inputValidation;
	@Autowired private CustomerOrderService customerOrderService;	

	@RequestMapping(value = "/report", method = { RequestMethod.GET }, produces = "application/json")
	public Map<String, Object> reportOrder(HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String startDate = request.getHeader("startDate");
		String endDate = request.getHeader("endDate");
		
		if (inputValidation.validateDate(startDate).equals(false)) {

			map.put("message", "invalid start date");
			map.put("status", HttpStatus.BAD_REQUEST);

		} else if (inputValidation.validateDate(endDate).equals(false)) {
			
			map.put("message", "invalid end date");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			
		} else {

			//Convert The String Date Input To Local Date And Format
			LocalDate start = inputValidation.formatDate(startDate);
			LocalDate end = inputValidation.formatDate(endDate);
			
			Long totalOrder = customerOrderService.getTotalOrder();
			Double totalOrderAmount = customerOrderService.getTotalOrderAmountByDate(start, end);
			String date = startDate + " to " + endDate;
			
			map.put("Date",  date);
			map.put("Total_Order",  totalOrder);
			map.put("Total_Order_Amount",   totalOrderAmount);
			map.put("message", "successful");
			map.put("status", HttpStatus.OK);				
							
		}
		
		return map;
			
	}	
	
}
