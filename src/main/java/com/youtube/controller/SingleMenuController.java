package com.youtube.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.VideoService;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Video;

@Controller
public class SingleMenuController {
	
	@Autowired
	private IVideoDAO videoDAO;
	@Autowired
	private VideoService videoService;

	@RequestMapping(method = RequestMethod.GET, value = "/playlist")
	public String singlePlaylist(Model model, 
			              @RequestParam(value = "playlistId") int playlistId) throws IllegalInputException, DataBaseException {
		
		List<Video> playlistVideos= videoDAO.getAllVideosFromPlaylist(playlistId);
		if(playlistVideos!=null&& !playlistVideos.isEmpty()){
			  VideoDTO currentVideo=videoService.playVideo(playlistVideos.get(0).getVideoId());
			    playlistVideos.remove(playlistVideos.get(0));
					
			
			    List<VideoTopViewDTO> otherVideos= new ArrayList<>();
			     for(Video video:playlistVideos){
			    	 otherVideos.add(new VideoTopViewDTO(video));
			     }
			    model.addAttribute("playlistId", playlistId);
			    model.addAttribute("currentVideo", currentVideo);
				model.addAttribute("playlistVideos", otherVideos);
		};
	  
		
		
		return "singlePlaylist";
	}

	@RequestMapping(value = { "/addToPlaylist", "/videos/addToPlaylist" }, method = RequestMethod.GET)
	public String addToPlaylist() {
		return "addToPlaylist";
	}
}
