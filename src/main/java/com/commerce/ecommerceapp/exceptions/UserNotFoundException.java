package com.commerce.ecommerceapp.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	String email;
	public UserNotFoundException(String message, String email) {
		super(message);
		this.email=email;
	}
	
	public String getEmail() {
		
		return email;
	}

}
