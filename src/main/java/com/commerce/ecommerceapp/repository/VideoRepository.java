package com.commerce.ecommerceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.commerce.ecommerceapp.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

	@Query("select v from Video v where v.title like %:searchVideo%")
	List<Video> searchVideosList(String searchVideo);
}
