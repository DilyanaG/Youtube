package com.youtube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.channel.ChannelDAO;
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.dto.channel.ProfileViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;


@Controller
public class ProfileController {
	@RequestMapping(value="/profile",method = RequestMethod.GET)
	public String profile(Model model,HttpServletRequest req) throws IllegalInputException, DataBaseException{
		
		System.out.println("v momenta otivash na kanal "+req.getParameter("channelId"));
		int channelId= Integer.valueOf(req.getParameter("channelId"));
		/* need data 
		            username
		            pictureUrl
		            all <list>videos-vidoTopViewDTO
		            followedChannels
		            
		 */ 
		        
		Channel  currentUser = ChannelDAO.getInstance().getChannelById(channelId);
		
		List <Video> videos= VideoDAO.getInstance().getVideosByChannelId(channelId);
		
		List<Channel> subscriptions= ChannelDAO.getInstance().getFollowedChannels(channelId);
		
		ProfileViewDTO profile = new ProfileViewDTO(currentUser,videos,subscriptions);
		System.out.println(profile);
		model.addAttribute("profile",profile);
		
		return "profile";
	}
}
