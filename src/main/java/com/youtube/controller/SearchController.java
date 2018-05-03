package com.youtube.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.model.pojo.Video;

@RestController
public class SearchController {

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Map<String, List<Video>> doGet() {
//	
//		?searchWord={searchWord}  get parametyr 
//	   put all videos   for parametyr
//		 videos has 
//		            videoId
//		            videoTitle
//                  channelId
//		            channelORusername
//           		pictureUrl
//                  views		
//		  maybe  create DTO for this VideoViewDTO      
		
		Map<String, List<Video>> result = new HashMap<String, List<Video>>();
		List<Video> playlists = new ArrayList<>();
		
		for(int i=0;i<10;i++){
			playlists.add(new Video());
		}
		result.put("playlists",playlists);

		return result;
	}
	
}
