package com.commerce.ecommerceapp.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VideoSearchApiData {
	
	@NotNull(message = "searchVideo can't be null")
	@NotBlank(message = "searchVideo can't be blank value")
	@Size(min = 2, message = "Min 2 charecters")
	private String searchVideo;
}
