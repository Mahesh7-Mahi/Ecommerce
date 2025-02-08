package com.commerce.ecommerceapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.ecommerceapp.dto.VideoResponseData;
import com.commerce.ecommerceapp.pojo.VideoSearchApiData;
import com.commerce.ecommerceapp.service.VideoService;
import com.commerce.ecommerceapp.utils.ResponseData;

import jakarta.validation.Valid;

@RestController
public class VideoController {
	
	@Autowired
	private VideoService videoService;
	
	@PostMapping("video/search")
	public ResponseEntity<?> videoSearchController(@Valid @RequestBody VideoSearchApiData videoSearchApiData){
	
		List<VideoResponseData> videos = videoService.searchingVideoService(videoSearchApiData);
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put(ResponseData.RESULT, ResponseData.SUCCESS);
		response.put(ResponseData.DATA, videos);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
		
	}

}
