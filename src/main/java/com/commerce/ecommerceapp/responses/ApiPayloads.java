package com.commerce.ecommerceapp.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiPayloads<T>{
	
	private String result;
	
	private String message;
	
	private T data;

}
