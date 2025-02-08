package com.commerce.ecommerceapp.scheduler;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.commerce.ecommerceapp.service.AWSS3FileUploadService;

//@Component
public class UploadLogFilesIntoS3AWS {
	
	
	@Autowired
	private AWSS3FileUploadService awss3FileUploadService;
	
	@Scheduled( fixedRate = 5000)
	public void testingSchediling() {
		
		System.out.println("Running scheduling method..");
		
	}
	
	//cron - "sec(0-59) min(0-59) hrs(0-23) Day(0-30) Month(0-11) Year weekDay(SUN.MON,TUE,THR,FRI,SAT,)"
	
	@Scheduled(cron = "0 0 1 * * * *")
	public void uploadLogFilesIntoAWSServer() throws Exception {
		
		
		/*
		 * logs directory
		 * get all files from the log folder
		 * loop -> upload for each file
		 */
		
		//to get logs directory
		String logFileDirectory = System.getProperty("user.dir"+"/logs");
		
		//get all files from the log folder
		File logFile = new File(logFileDirectory);
		
		//get all files into files array
		File[] logFilesList = logFile.listFiles();
		
		
		//loop -> upload for each file and upload into AWS cloud S3 Bucket
		for(File logs: logFilesList) {
			awss3FileUploadService.uploadLogFilesToCloud(logs,"logs/"+logs.getName());
			System.out.println("Uploaded :logs/"+logs.getName());
		}
		
	}

}
