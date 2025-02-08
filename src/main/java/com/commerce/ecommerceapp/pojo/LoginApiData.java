package com.commerce.ecommerceapp.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginApiData {
	
	@NotNull(message = "email required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "email is in proper format")
	private String email;
	
	@NotNull(message = "password required")
	@Size(min=8, message = "password length must be 8 or above")
	private String password;
}
