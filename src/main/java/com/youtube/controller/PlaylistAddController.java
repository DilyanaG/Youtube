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
import com.youtube.model.dao.playlist.PlaylistDAO;
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.dto.playlist.ChannelPlaylistDTO;

@Controller
public class PlaylistAddController {
	
	@Autowired
	private PlaylistDAO playlistDao;
	@Autowired
	private VideoDAO videoDao;
  
	@RequestMapping(value = "/addToPlaylist", method = RequestMethod.GET)
	public String addToPlaylist(Model model, HttpServletRequest req, HttpSession session)
			throws IllegalInputException, DataBaseException {

		int videoId = Integer.valueOf(req.getParameter("videoId"));
		int channelId = (int) session.getAttribute("channelId");
		List<ChannelPlaylistDTO> playlists = playlistDao.getAllChannelPlaylists(channelId);
		model.addAttribute("playlists", playlists);
		model.addAttribute("videoId", videoId);
		return "addToPlaylist";
	}
	
	
	@RequestMapping(value = "/addVideoToPlaylist", method = RequestMethod.POST)
	public String addVideo(@RequestParam(value = "playlistId") int playlistId,
		                	@RequestParam(value = "videoId") int videoId)
			throws IllegalInputException, DataBaseException {
		
		videoDao.addVideoToPlaylist(videoId, playlistId);
		System.out.println(videoId+"ti doide do tuka"+playlistId);
		return "redirect:/playlist?playlistId="+playlistId;
	}
	
	
}
