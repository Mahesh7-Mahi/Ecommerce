package com.commerce.ecommerceapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.commerce.ecommerceapp.utils.ResponseData;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> requestDataValdationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors=new HashMap<String,String>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		
		Map<String, Object> responseMap=new HashMap<String, Object>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.FAILED);
		responseMap.put(ResponseData.ERROR, errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> genericException(Exception ex) {
		
		Map<String, String> responseMap=new HashMap<String, String>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.FAILED);
		responseMap.put(ResponseData.ERROR, ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
	}

}
