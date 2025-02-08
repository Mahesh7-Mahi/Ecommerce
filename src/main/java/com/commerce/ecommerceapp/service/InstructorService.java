package com.commerce.ecommerceapp.service;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.dto.InstructorDTO;
import com.commerce.ecommerceapp.entity.Instructor;
import com.commerce.ecommerceapp.repository.InstructorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class InstructorService {
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	@Transactional
	public Instructor InstructorDetailsService(Long id) throws JsonMappingException, JsonProcessingException {
		
		
		
		Instructor instructor = new Instructor();
		
		//System.out.println(instructor);
		
		//instructor.setInstructorId(Long.valueOf(instructor.getInstructorId()));
		
		instructor = (Instructor) redisTemplate.opsForValue().get("instructor_"+id);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    instructor = objectMapper.convertValue(instructor, Instructor.class);
		
		System.out.println(instructor);
		
		
//		if (cached instanceof LinkedHashMap) {
//		    ObjectMapper objectMapper = new ObjectMapper();
//		    instructor = objectMapper.convertValue(cached, Instructor.class);
//		    System.out.println("Converted LinkedHashMap to Instructor: " + instructor);
//		} else if (cached instanceof Instructor) {
//		    System.out.println("Fetched Instructor directly: " + cached);
//		} else {
//		    System.out.println("Cache miss! Fetching from DB...");
//		    instructor = instructorRepository.getAllInstructorDetailsById(id);
//		    redisTemplate.opsForValue().set("instructorCache::" + id, instructor);
//		}
		
		if (instructor == null) {
			
			instructor = instructorRepository.getAllInstructorDetailsById(id);
			
			//instructor.setCourses(new ArrayList<>(instructor.getCourses()));
			
			//instructor.setInstructorId(Long.valueOf(((InstructorDTO) instructor).getInstructorId()));
		
			redisTemplate.opsForValue().set("instructor_"+id, instructor);
			
		}
		//instructor = instructorRepository.getAllInstructorDetailsById(id);
	
		return instructor;
	}
	

}
