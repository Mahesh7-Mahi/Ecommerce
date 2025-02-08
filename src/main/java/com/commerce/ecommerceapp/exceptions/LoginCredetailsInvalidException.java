package com.commerce.ecommerceapp.exceptions;

public class LoginCredetailsInvalidException extends RuntimeException{

	String email;
	public LoginCredetailsInvalidException(String message,String email) {
		super(message);
		this.email=email;
	}
	
	public String getEmail() {
		
		return email;
	}
}
