package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ecommerceapp.pojo.MoneyTransferAccountDetailsApiData;
import com.commerce.ecommerceapp.service.AuthService;
import com.commerce.ecommerceapp.utils.ResponseData;

@RestController
public class MoneyTransfer {
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/transfer")
	public ResponseEntity<?> moneyTransferHnadler(@RequestBody MoneyTransferAccountDetailsApiData moneyTransferAccountDetailsApiData) throws Exception{
		
		Map<String, Object> moneyTransferMap = authService.moneyTransferHandlingService(moneyTransferAccountDetailsApiData);
		
		Map<String, Object> responseMap=new HashMap<String, Object>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.DATA, moneyTransferMap);
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}

}
