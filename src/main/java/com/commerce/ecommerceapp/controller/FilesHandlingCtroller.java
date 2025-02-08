package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.commerce.ecommerceapp.service.EmployeeDetailsCsvToDBService;
import com.commerce.ecommerceapp.service.FileUploadService;
import com.commerce.ecommerceapp.service.JwtService;
import com.commerce.ecommerceapp.utils.ResponseData;

import io.jsonwebtoken.Claims;


@RestController
@RequestMapping("/upload")
public class FilesHandlingCtroller {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmployeeDetailsCsvToDBService employeeDetailsCsvToDBService;
	/*
	 * After login
	 * Receive Authorized header
	 * check if it is null
	 * check if it start with bearer
	 * get token with out bearer
	 * check it is valid or not
	 * check if it is expired or not
	 * if all are okay, then proceed to operation else throw error
	 */
	
	@PostMapping("images")
	public ResponseEntity<?> imageFilesHandling(@RequestHeader("Authorization") String jwtTokenHeader,@RequestParam("file") MultipartFile inputFile) throws Exception {
		
		System.out.println(jwtTokenHeader);
		
		if(jwtTokenHeader == null || jwtTokenHeader.startsWith("Bearer") == false) {
			
			throw new Exception("Unauthorized action performed, your not allowed to perform this operation");
		}
		
		jwtTokenHeader = jwtTokenHeader.substring(7);
		
		System.out.println(jwtTokenHeader);
		
		Boolean isValidToken = jwtService.verifyJwtToken(jwtTokenHeader);
		
		System.out.println(isValidToken);
		
		Claims claims = jwtService.getJwtClaim(jwtTokenHeader);
		
		System.out.println(claims.get("id"));
		
		System.out.println(claims.get("email"));
		
		System.out.println(claims.get("name"));
		
		
		
		fileUploadService.imageFileHandlingService(inputFile);
		Map<String, String> responseMap=new HashMap<>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "file uploaded successfully.");
		
		//responseMap.put("file-data",inputFile);
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@PostMapping("pdfs")
	public ResponseEntity<?> pdfFilesHandling(@RequestParam("file") MultipartFile inputFile) throws Exception {
		
		fileUploadService.pdfFileHandlingService(inputFile);
		Map<String, String> responseMap=new HashMap<>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "file uploaded successfully.");
		
		//responseMap.put("file-data",inputFile);
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	/*
	 * create a path for csv file upload
	 * check correct type of file is uploaded or not
	 * if uploaded file is .csv -> proceed to process the .csv file
	 * using bufferedReder read the file 
	 * using CSVReader class get the inside the file data into array of objects
	 * after using the string array read the data and insert into db using saveAll() method
	 */
	
	@PostMapping("insertcsvfileintodb")
	public ResponseEntity<?> insetCSVDataIntoDb(@RequestParam("csv_file") MultipartFile EmployeeDataCsvFile) throws Exception{
		
		employeeDetailsCsvToDBService.insertEmployeeDetilsIntoDb(EmployeeDataCsvFile);
		
		Map<String, String> responseMap=new HashMap<>();
		
		responseMap.put(ResponseData.RESULT, ResponseData.SUCCESS);
		responseMap.put(ResponseData.MESSAGE, "CSV file data inserted into DB successfully");
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}

}
