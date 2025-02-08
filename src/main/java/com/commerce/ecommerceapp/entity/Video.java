package com.commerce.ecommerceapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "videos")
public class Video {
	
	//video_id,title,description,video_path,channel_id,
	//no_of_likes,no_of_dislikes,thumbnile_path
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "video_id")
	private int id;
	
	
	private String title;
	
	private String description;
	private String videoPath;
	private int channelId;
	private int noOfLikes;
	private int noOfDislikes;
	private String thumbnilePath;
	
	

}
