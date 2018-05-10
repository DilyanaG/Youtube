package com.youtube.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.dto.video.LikesDTO;


@RestController
public class LikeDislikeController {

	@Autowired
	private VideoDAO videoDAO;
	
	@RequestMapping(value = "/likes ", method = RequestMethod.POST)
	public LikesDTO like(@RequestParam(value = "videoId") int videoId,
						@RequestParam(value = "isLike") boolean isLike,
						HttpSession session) throws IllegalInputException, DataBaseException {
		
		int channelId = (int) session.getAttribute("channelId");
	
		return videoDAO.likeDislikeVideo(videoId, channelId, isLike);
	}
}
