package com.commerce.ecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commerce.ecommerceapp.entity.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{

	@Query(value = "select i from Instructor i left join fetch i.courses where i.instructorId = :insId")
	Instructor getAllInstructorDetailsById(@Param("insId") Long insId);
}
