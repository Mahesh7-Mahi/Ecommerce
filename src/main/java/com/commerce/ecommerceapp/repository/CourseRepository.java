package com.commerce.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerce.ecommerceapp.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	
	

}
