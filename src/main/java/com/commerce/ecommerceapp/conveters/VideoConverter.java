package com.commerce.ecommerceapp.conveters;


import com.commerce.ecommerceapp.dto.VideoResponseData;
import com.commerce.ecommerceapp.entity.Video;

public class VideoConverter {
	
	public static VideoResponseData videoResponseConverter(Video video) {
		
        VideoResponseData videoResponseData = new VideoResponseData();
		
		videoResponseData.setId(video.getId());
		
		videoResponseData.setTitle(video.getTitle());
		
		videoResponseData.setDescription(video.getDescription());
		
		return videoResponseData;
	}

}
