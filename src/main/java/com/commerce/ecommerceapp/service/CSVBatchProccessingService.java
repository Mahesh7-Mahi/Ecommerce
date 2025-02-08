package com.commerce.ecommerceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.entity.Employee;
import com.commerce.ecommerceapp.repository.EmployeeRepository;

@Service
public class CSVBatchProccessingService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Async("CSVBatchProcessing")
	public void csvBtachProcessingServiceImpl(List<Employee> employees) throws Exception {
		try {
			employeeRepository.saveAll(employees);
			
		} catch (Exception e) {
			throw new Exception("CSV file not processed successfully, please check again");
		}
		
	}

}
