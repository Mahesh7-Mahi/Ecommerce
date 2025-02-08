package com.commerce.ecommerceapp.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class AWSS3FileUploadService {
	
	/*
	 * aws packages -> s3(file upload, delete etc), auth
	 * aws credetails ->
	 * aws authenticate
	 * use s3 file upload method
	 */
	
	@Value("${aws.s3.bucket.name}") 
	private String bucketName;
	
	private S3Client s3Client;
	
	public AWSS3FileUploadService(@Value("${aws.accessKeyId}") String accessKeyId,@Value("${aws.secretKey}") String secretKey,@Value("${aws.region}") String region ) {
		
		//create credetails AwsBasicCredentials using accessakaey and SecretKey
		AwsBasicCredentials awsBasicCredentials=AwsBasicCredentials.create(accessKeyId, secretKey);
		
		
		//provide the credentials details using credentailsProvider(StaticcredetailsProvider.create(awsBasicCredetails)) method
		//and provide details using region(Region.of(region))
		//By using this we create S3Client using .builder() and .build() method 
		//In this kind of declarative approche we start with .builder() method and end with .build() method 
		s3Client = S3Client.builder().region(Region.of(region)).credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials)).build();
		
	}

	public void uploadFilesToCloud(MultipartFile inputFile, String fileNme) throws Exception {
		
		/*
		 * create put request
		 * call s3 put object method
		 */
		
		//we create the put request using PutObjectRequest using .bucket(bucketName) and .key(fileName)
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileNme).build();
		
		//we give the inputFile and file size using RequestBody.fromInputStream(inputFile.getInputStream(), inputFile.getSize())
		s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputFile.getInputStream(), inputFile.getSize()));
		
	}
	
	public void uploadLogFilesToCloud(File inputFile, String fileNme) throws Exception {
		
		/*
		 * create put request
		 * call s3 put object method
		 */
		
		//we create the put request using PutObjectRequest using .bucket(bucketName) and .key(fileName)
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileNme).build();
		
		//we give the inputFile and file size using RequestBody.fromInputStream(inputFile.getInputStream(), inputFile.getSize())
		s3Client.putObject(putObjectRequest,inputFile.toPath());
		
	}
	
	
}
