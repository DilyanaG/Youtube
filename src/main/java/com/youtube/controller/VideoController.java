package com.youtube.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.VideoService;
import com.youtube.model.dao.channel.IChannelDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;

@Controller
public class VideoController {

	private static final int MAX_VIDEOS_FOR_PAGE = 15;
	
	@Autowired
	private IVideoDAO videoDAO;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private IChannelDAO channelDAO;
	
	
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public String playVideo(Model model, HttpServletRequest req) throws Exception {

		int videoId = Integer.valueOf(req.getParameter("videoId"));
		VideoDTO video = videoService.playVideo(videoId);

		if(req.getSession().getAttribute("channelId")!=null){
			List<Channel> followedChannels = channelDAO.getFollowedChannels((int)(req.getSession().getAttribute("channelId")));
			for(Channel folowed:followedChannels){
			        if(folowed.getChannelId()==video.getChannelId()){
			        	model.addAttribute("subscribe","true");
			        }
			}
		}
		
		model.addAttribute("video", video);
		return "single";
	}
}
