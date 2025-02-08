package com.commerce.ecommerceapp.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Autowired
	private AWSS3FileUploadService awss3FileUploadService;
	
	@Value("${file.image.upload.path}")
	private String IMAGE_UPLOAD_PATH;
	
	@Value("${file.pdf.upload.path}")
	private String PDF_UPLOAD_PATH;

	
	private int MAX_ALLOWED_SIZE = 5*1024*1024;
	/*
	 * Get the file
	 * sanitize the file and check the file type if it was allowed type,then proceed for size validation.
	 * check the size of the file if it was allowed size, 
	 * then proceed for upload that file into particuler path.
	 */
	public void imageFileHandlingService(MultipartFile inputFile) throws Exception {
		
		 String filename = StringUtils.cleanPath(inputFile.getOriginalFilename());
		 
		 String fileType= StringUtils.getFilenameExtension(filename);
		 
		 List<String> imagesTypeList= new ArrayList<String>();
		 imagesTypeList.add("tiff");
		 imagesTypeList.add("png");
		 imagesTypeList.add("jpeg");
		 imagesTypeList.add("gif");
		 
		 Boolean isValidFileType=imagesTypeList.stream().anyMatch(types -> types.equals(fileType) );
		 
		 if(isValidFileType == false) {
			 throw new Exception("File type is not allowed, please upload correct file");
		 }
		 
		 long fileSize = inputFile.getSize();
		 
		 if(MAX_ALLOWED_SIZE < fileSize) throw new Exception("Maximum file size is 5MB");
		 
		 String imageNewName=UUID.randomUUID().toString()+ "." +fileType;
		 
		 Path path=Paths.get(IMAGE_UPLOAD_PATH + imageNewName);
		 
		 Files.copy(inputFile.getInputStream(), path);
		 
		 
	}
	
	public void pdfFileHandlingService(MultipartFile inputFile) throws Exception {
		
		String fileName = StringUtils.cleanPath(inputFile.getOriginalFilename());
		
		String fileType = StringUtils.getFilenameExtension(fileName);
		
		String type="pdf";
		
		if(!(fileType.equals(type))) throw new Exception("File type is not allowed, please upload correct file");
		
		long fileSize= inputFile.getSize();
		
		if(MAX_ALLOWED_SIZE < fileSize) throw new Exception("Maximum file size is 5MB");
		
		String pdfNewName=UUID.randomUUID().toString()+ "." +fileType;
		
		Path path=Paths.get(PDF_UPLOAD_PATH + pdfNewName );
		
		Files.copy(inputFile.getInputStream(),path);
		
		awss3FileUploadService.uploadFilesToCloud(inputFile, fileName);
		
	}
}
