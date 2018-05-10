package com.youtube.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.model.dao.comment.ICommentDAO;

@RestController
public class CommentController {

	@Autowired
	private ICommentDAO commentDAO;
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String writeComment(@RequestParam(value = "message") String message,
							   @RequestParam(value = "videoId") int videoId,
							   HttpSession session, Model model) throws Exception {

		int channelId = (int) session.getAttribute("channelId");
		commentDAO.addNewCommentToVideo(videoId, channelId, message);
		return "";
	}
}
