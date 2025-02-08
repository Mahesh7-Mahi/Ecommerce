package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ecommerceapp.dto.InstructorDTO;
import com.commerce.ecommerceapp.entity.Instructor;
import com.commerce.ecommerceapp.service.InstructorService;
import com.commerce.ecommerceapp.utils.ResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class InstructorController {
	
	@Autowired
	private InstructorService instructorService;
	
	@GetMapping("instructor/{id}")
	public ResponseEntity<?> getAllInstructorDetails(@Parameter(description = "instructor id", example = "1") @PathVariable Long id) throws JsonMappingException, JsonProcessingException{
		
		//Instructor instructor = new Instructor();
		
		Object instructor = instructorService.InstructorDetailsService(id);
		
		Map<String, Object> responseMap=new HashMap<String, Object>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.DATA, instructor);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}

}
