package com.youtube.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.VideoService;
import com.youtube.model.dao.channel.IChannelDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.LikesDTO;
import com.youtube.model.dto.video.OtherVideosDTO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;

@Controller
public class VideoController {

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

		if (req.getSession().getAttribute("channelId") != null) {
			List<Channel> followedChannels = channelDAO
					.getFollowedChannels((int) (req.getSession().getAttribute("channelId")));
			for (Channel folowed : followedChannels) {
				if (folowed.getChannelId() == video.getChannelId()) {
					model.addAttribute("subscribe", "true");
				}
			}
		}
		model.addAttribute("video", video);
		
		List<OtherVideosDTO> suggestedVideos = videoDAO.getOtherVideos(videoId);
		model.addAttribute("suggestedVideos", suggestedVideos);
		
		return "single";
	}
	
	@RequestMapping(value = "/likes ", method = RequestMethod.POST)
	public LikesDTO like(@RequestParam(value = "videoId") int videoId,
						@RequestParam(value = "isLike") boolean isLike,
						HttpSession session) throws IllegalInputException, DataBaseException {
		
		int channelId = (int) session.getAttribute("channelId");
		
		return videoDAO.likeDislikeVideo(videoId, channelId, isLike);
	}
	
	@RequestMapping(value = "/suggestions", method = RequestMethod.GET)
	public String giveSuggestions(Model model, HttpServletRequest req) throws Exception {

		List<Video> videos = videoDAO.getMostPopularVideos();
		model.addAttribute("suggestions", videos);
		return "";
	}
}
