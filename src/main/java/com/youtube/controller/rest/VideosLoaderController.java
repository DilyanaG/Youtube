package com.youtube.controller.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Video;

@RestController
public class VideosLoaderController {
	@Autowired 
	private IVideoDAO videoDao;
	
	@RequestMapping(value = "/channelVideos ", method = RequestMethod.GET)
	public Map<String, List<Object>>  loadVideo(@RequestParam(value = "channelId", required = true) int channelId,
			                            @RequestParam(value = "sortBy", required = true) String sortBy ) throws IllegalInputException, DataBaseException, SQLException{
		List<Video> videos= new ArrayList<>();
		switch (sortBy) {
		case "date":
			videos=videoDao.ByChannelIdByDate(channelId);
			break;
		case "title":
			videos=videoDao.ByChannelIdByTitle(channelId);
			break;
		case "views":
			videos=videoDao.ByChannelIdByViews(channelId);
			break;
        default:
        	videos= videoDao.getVideosByChannelId(channelId);
        	Collections.sort(videos,(v1,v2)->v1.getLikes()-v2.getLikes());
			break;
		}
				//videoDao.getVideosByChannelId(channelId);
		List<Object> sendVideos=new ArrayList<>();
		for(Video v:videos){
                sendVideos.add(new VideoTopViewDTO(v));
		}
		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
		result.put("videos", sendVideos);
		return result;
	}
	
	@RequestMapping(value = "/deleteVideo ", method = RequestMethod.DELETE)
	public String  deleteVideo(@RequestParam(value = "videoId", required = true) int videoId,
			                       HttpSession session) throws IllegalInputException, DataBaseException{
		
		videoDao.deleteVideo(videoId);
		return "";
	}
	

}
