package com.commerce.ecommerceapp.dto;

import java.io.Serializable;
import java.util.List;

import com.commerce.ecommerceapp.entity.Course;
import com.commerce.ecommerceapp.entity.Instructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorDTO implements Serializable {
	
	private Long instructorId;
    private String instructorName;
    private int instructorExperience;
    private String instructorPhoneNumber;
    
    private List<Course> courses;

    public InstructorDTO(Instructor instructor) {
        this.instructorId = instructor.getInstructorId();
        this.instructorName = instructor.getInstructorName();
        this.instructorExperience = instructor.getInstructorExperience();
        this.instructorPhoneNumber = instructor.getInstructorPhoneNumber();
        this.courses = instructor.getCourses();
    }
}
