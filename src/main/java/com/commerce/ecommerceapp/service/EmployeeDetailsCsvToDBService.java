package com.commerce.ecommerceapp.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.commerce.ecommerceapp.entity.Employee;

@Service
public class EmployeeDetailsCsvToDBService {
	
	@Autowired
	private CSVBatchProccessingService csvBatchProccessingService;
	
	public void insertEmployeeDetilsIntoDb(MultipartFile EmployeeDataCsvFile) throws Exception{
		
		String fileName = StringUtils.cleanPath(EmployeeDataCsvFile.getOriginalFilename());
		
		String fileType = StringUtils.getFilenameExtension(fileName);
		
		if (fileType.equals("csv") == false) {
			throw new Exception("Please upload csv file only..");
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(EmployeeDataCsvFile.getInputStream()))){
			
			String readLine;
			
			String[] csvRow = bufferedReader.readLine().toString().split(",",-1);
			
			List<Employee> employees = new ArrayList<Employee>();
			
			while ( (readLine=bufferedReader.readLine())!= null) {
				
				csvRow = readLine.toString().split(",",-1);
				
				Employee employee = new Employee();
				employee.setEmployeeId(csvRow[0]);
				employee.setEmplyoeeName(csvRow[1]);
				employee.setSalary(csvRow[2]);
				employee.setAddress(csvRow[3]);
				
				employees.add(employee);
				
				if (employees.size()==1000) {
					csvBatchProccessingService.csvBtachProcessingServiceImpl(new ArrayList<Employee>(employees));
					employees.clear();
				}	
				
			}
			
			if(employees.size()>0) {
				csvBatchProccessingService.csvBtachProcessingServiceImpl(new ArrayList<Employee>(employees));
				employees.clear();
			}
		}catch (Exception e) {
			throw new Exception("please upload non empty CSV file.");
		}
		
		
//		System.out.println(bufferedReader.readLine());
//		Reader reader = new BufferedReader(new InputStreamReader(EmployeeDataCsvFile.getInputStream()));
//		CSVReader csvReader = new CSVReader(reader);
//		System.out.println(Arrays.toString(csvReader.readNext()));
//		String[] employee = csvReader.readNext();
//		List<Employee> employees=null;
//		employeeRepository.saveAll(employees);		
	}

}
