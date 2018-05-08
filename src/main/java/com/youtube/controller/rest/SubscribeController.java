package com.youtube.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.channel.IChannelDAO;

@RestController
public class SubscribeController {
	@Autowired
	private IChannelDAO channelDAO;

	@RequestMapping(value = "/subscribe ", method = RequestMethod.POST)
	public String subscribe(@RequestParam(value = "channel", required = true) int channelId, HttpSession session)
			throws IllegalInputException, DataBaseException {
		System.out.println(channelId);
		System.out.println("subscribe user ");
		int follower = (int) session.getAttribute("channelId");
		List<Integer> followers = channelDAO.getFollowedChannelIds(channelId);
		if (!followers.contains(follower)) {
			channelDAO.followChannel(follower, channelId);
		}
		return "";
	}

	@RequestMapping(value = "/subscribe ", method = RequestMethod.DELETE)
	public String unSubscribe(@RequestParam(value = "channel", required = true) int followed, HttpSession session)
			throws IllegalInputException, DataBaseException {
		System.out.println(followed);
		System.out.println("UNSubscribe user ");

		int follower = (int) session.getAttribute("channelId");
		channelDAO.unfollowChannel(follower, followed);

		return "";
	}

	@RequestMapping(value = "/subscribe ", method = RequestMethod.GET)
	public String isAlreadySubscribed(@RequestParam(value = "channel", required = true) int channelId,
			HttpSession session, Model model) throws IllegalInputException, DataBaseException {
		int follower = (int) session.getAttribute("channelId");
		List<Integer> followers = channelDAO.getFollowedChannelIds(channelId);
		if (!followers.contains(follower)) {
			model.addAttribute("subscribe", "true");
		}
		return "";
	}

}
