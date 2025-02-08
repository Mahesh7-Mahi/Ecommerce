package com.commerce.ecommerceapp.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpApiData {
	
	@Schema(description = "this name indicates user name in string format", example = "Mahesh Reddy")
	@NotNull(message = "Name is missing")
	@Size(min = 2, message = "name lenth must be above 2 cahrecters")
	private String name;
	
	@Schema(description = "you have to enter your email address in proper format", example = "reddyymahesh76@gmail.com")
	@NotNull(message = "Email is missing")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email must be in proper format")
	private String email;
	
	@Schema(description = "enter your passowrd in strong format", example = "Mahesh@123")
	@NotNull(message = "Password is missing")
	@Size(min = 8, message = "password lenth must be above 8 cahrecters")
	private String password;
	
	
	@Schema(description = "enter your 10 digit mobile number ", example = "9989209508")
	@NotNull(message = "Mobile number is missing")
	@Size(min = 10, message = "mobile number lenth must be 10")
	private String mobile;
}
