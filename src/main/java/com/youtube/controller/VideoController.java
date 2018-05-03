package com.youtube.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Video;

@RestController
public class VideoController {

	
	
	@RequestMapping(value = "/videoLoader", method = RequestMethod.GET)
	public Map<String, List<Object>> doGet() {
		
		
		//get paramether here  if recent get videos by recent
		//or most by view 
		//and by search 
	    //and return the ordered videos with paramether 
		             // videoid
		           
		System.out.println("IAM video loader hiiii ");
		List<Object> videos = new ArrayList<>();
		List<Object> comments = new ArrayList<>();
		for(int i=0;i<10;i++){
			Video video1 = new Video();
			video1.setVideoId(i);
			videos.add(video1);
			comments.add(new Comment());
		}
		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
	
		result.put("videos",videos);
		result.put("comments",comments);
		return result;
	}
}
