package com.youtube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.channel.IChannelDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.channel.ProfileViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;


@Controller
public class ProfileController {
	
	@Autowired
	private IChannelDAO channelDao;
	
	@Autowired
	private IVideoDAO videoDao;
	
	
	
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
		        
		Channel  currentUser =channelDao.getChannelById(channelId);
		
		List <Video> videos= videoDao.getVideosByChannelId(channelId);
		
		List<Channel> subscriptions= channelDao.getFollowedChannels(channelId);
		 
		ProfileViewDTO profile = new ProfileViewDTO(currentUser,videos,subscriptions);
		System.out.println(profile);
		
		if(req.getSession().getAttribute("channelId")!=null){
			for(Channel folowed:channelDao.getFollowedChannels((int)(req.getSession().getAttribute("channelId")))){
			        if(folowed.getChannelId()==channelId){
			        	model.addAttribute("subscribe","true");
			        }
			}
		}
		
		
		model.addAttribute("profile",profile);
		
		return "profile";
	}
}
