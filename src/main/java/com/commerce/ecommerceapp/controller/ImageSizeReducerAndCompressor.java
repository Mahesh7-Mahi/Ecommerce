package com.commerce.ecommerceapp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

@RestController
public class ImageSizeReducerAndCompressor {

	@PostMapping("/image-resize")
	public ResponseEntity<?> imageResize(@RequestParam("image-file") MultipartFile multipartFile) throws IOException{
		
		File tempFile = File.createTempFile("upload_", ".png");
		
		multipartFile.transferTo(tempFile);
		
		Thumbnails.of(tempFile).size(68, 68).keepAspectRatio(false).toFile("uploads/resize_68x68.png");
		
		Thumbnails.of(tempFile).size(100, 100).keepAspectRatio(false).toFile("uploads/resize_100x100.png");
		
		return ResponseEntity.status(HttpStatus.OK).body("thumbnile created");
	}
	
	@PostMapping("/image-size-reduce")
	public ResponseEntity<?> imageSizeReducer(@RequestParam("image-file") MultipartFile multipartFile) throws IOException{
		
		ByteArrayOutputStream resizeByteArrayOutputStream = new ByteArrayOutputStream();
		
		Thumbnails.of(multipartFile.getInputStream()).scale(1.0).outputQuality(0.1).toOutputStream(resizeByteArrayOutputStream);
		
		byte[] imageInBytes = resizeByteArrayOutputStream.toByteArray();
		
		//System.out.println(imageInBytes);
		
		File tempFile = new File("reduced_image_quality.jpg");
		
		try(FileOutputStream fileOutputStream = new FileOutputStream(tempFile)){
			
			fileOutputStream.write(imageInBytes);
			
		}
		return ResponseEntity.status(HttpStatus.OK).body("thumbnile created");
		
	}
}
