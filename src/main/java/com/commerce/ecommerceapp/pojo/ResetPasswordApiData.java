package com.commerce.ecommerceapp.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordApiData {
	
	@NotNull(message = "password is required")
	@Size(min=8, message = "minimum 8 chracters are required")
	private String password;
	
	@NotNull(message = "confirm-password is required")
	@Size(min=8, message = "minimum 8 chracters are required")
	private String confirmPssword;
	
	@NotNull(message = "linkId is required")
	@Size(min=8, message = "minimum 8 chracters are required")
	private String passwordResetKey;
}
