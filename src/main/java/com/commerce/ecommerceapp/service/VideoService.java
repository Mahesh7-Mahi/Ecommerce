package com.commerce.ecommerceapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ecommerceapp.conveters.VideoConverter;
import com.commerce.ecommerceapp.dto.VideoResponseData;
import com.commerce.ecommerceapp.entity.Video;
import com.commerce.ecommerceapp.pojo.VideoSearchApiData;
import com.commerce.ecommerceapp.repository.VideoRepository;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository videoRepository;
	
	

	public List<VideoResponseData> searchingVideoService(VideoSearchApiData videoSearchApiData) {
		
		
		List<Video> videos = videoRepository.searchVideosList(videoSearchApiData.getSearchVideo());
//		
//		List<VideoResponseData> videoResponseDatas = new ArrayList<VideoResponseData>();
//		
//		for(Video video: videos) {
//			videoResponseDatas.add(VideoConverter.videoResponseConverter(video));
//		}
		
		List<VideoResponseData> videoResponseDatas = videos.stream().map(video -> VideoConverter.videoResponseConverter(video)).collect(Collectors.toList());
		
		return videoResponseDatas;
	}
}
