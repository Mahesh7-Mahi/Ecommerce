package com.commerce.ecommerceapp.pojo;

import lombok.Data;

@Data
public class MoneyTransferAccountDetailsApiData {
	
	private String fromAccount;
	
	private String toAccount;
	
	private Double amount;

}
