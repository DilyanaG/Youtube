package com.youtube.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.channel.ChannelDAO;

@RestController
public class SubscribeController {
   @Autowired 
   private ChannelDAO channelDao;
	
	@RequestMapping(value = "/subscribe ", method = RequestMethod.GET)
	public String  subscribe(@RequestParam(value = "channel", required = true) int channelId,
			                 HttpSession session) throws IllegalInputException, DataBaseException{
		System.out.println(channelId);
		System.out.println("subscribe user ");
		channelDao.followChannel((int)session.getAttribute("channelId"),channelId);
		return "";
	}
	
	@RequestMapping(value = "/subscribe ", method = RequestMethod.DELETE)
	public String  unSubscribe(@RequestParam(value = "channel", required = true) int channelId,
			                       HttpSession session) throws IllegalInputException, DataBaseException{
		System.out.println(channelId);
		System.out.println("UNSubscribe user ");
		channelDao.unfollowChannel((int)session.getAttribute("channelId"),channelId);
		return "";
	}
}
