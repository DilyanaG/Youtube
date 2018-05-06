package com.youtube.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.pojo.Video;

@Controller
public class SingleMenuController {
	
	@Autowired
	private IVideoDAO videoDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/video")
	public String singleVideo(Model model, HttpServletRequest req) throws Exception {

		int videoId = Integer.valueOf(req.getParameter("videoId"));
		Video video = videoDAO.getVideoById(videoId);

		model.addAttribute("video", video);
		return "single";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/playlist")
	public String singlePlaylist(Model model, HttpServletRequest req) {
		System.out.println(req.getParameter("playlistId"));
		// get playlist parametyr here
		Integer[] videoss = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1 };
		List<Video> videos = new ArrayList<>();
		for (int i = 1; i < 15; i++) {
			Video video = new Video();
			video.setVideoId(i);
			videos.add(video);
		}
		model.addAttribute("currentVideoComments", videos);
		return "singlePlaylist";
	}

	@RequestMapping(value = { "/addToPlaylist", "/videos/addToPlaylist" }, method = RequestMethod.GET)
	public String addToPlaylist() {
		return "addToPlaylist";
	}
}
