package com.youtube.controller.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.VideoService;
import com.youtube.model.dao.playlist.IPlaylistDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.playlist.ChannelPlaylistDTO;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;

@RestController
public class PlaylistController {

	@Autowired
	private IPlaylistDAO playlistDao;
	@Autowired
	private IVideoDAO videoDao;
	@Autowired
	private VideoService videoService;

	@RequestMapping(value = "/playlists", method = RequestMethod.GET)
	public Map<String, List<Object>> doGet(@RequestParam(value = "channelId", required = true) int channelId,
			@RequestParam(value = "sortBy", required = true) String sortBy)
			throws DataBaseException, IllegalInputException {

		// int channelId=Integer.valueOf(req.getParameter("channelId"));

		List<Object> sendPlaylist = new ArrayList<>();
		// get playlists for channel
		List<Playlist> playlists = playlistDao.getPlaylistsByChannelAndSortByCreationDate(channelId);
		// create PlaylistTopViewDTO's for playlists and add in list

		switch (sortBy) {
		case "name":
			Collections.sort(playlists, (p1, p2) -> p1.getPlaylistName().compareTo(p2.getPlaylistName()));
			break;
		case "last":
			Collections.sort(playlists, (p1, p2) -> p2.getLastVideoUploaded().compareTo(p1.getLastVideoUploaded()));
			break;
		default:
			break;
		}

		for (Playlist playlist : playlists) {
			List<Video> playlistVideos = videoDao.getAllVideosFromPlaylist(playlist.getPlaylistId());
			sendPlaylist.add(new PlaylistTopViewDTO(playlist, playlistVideos));
		}

		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
		result.put("playlists", sendPlaylist);
		return result;
	}

	@RequestMapping(value = "/changeVideo", method = RequestMethod.GET)
	public Map<String, List<Object>> getVideoByID(@RequestParam(value = "playlistId", required = true) int playlistId,
			@RequestParam(value = "videoId", required = true) int videoId)
			throws DataBaseException, IllegalInputException {

		List<Video> playlistVideos = videoDao.getAllVideosFromPlaylist(playlistId);
		List<Object> otherVideos = new ArrayList<>();
		int currentVideoIndex = 0;
		for (int i = 0; i < playlistVideos.size(); i++) {
			otherVideos.add(new VideoTopViewDTO(playlistVideos.get(i)));
			if (playlistVideos.get(i).getVideoId() == videoId) {
				currentVideoIndex = i;
			}
		}
		List<Object> currentVideo = new ArrayList<>();
		currentVideo.add(videoService.playVideo(videoId));
		otherVideos.remove(currentVideoIndex);

		Map<String, List<Object>> result = new HashMap<>();
		result.put("currentVideo", currentVideo);
		result.put("comments", otherVideos);
		result.put("playlistVideos", otherVideos);

		return result;
	}

	@RequestMapping(value = "/addNewPlaylist", method = RequestMethod.POST)
	public Map<String, List<ChannelPlaylistDTO>> createNewPlaylist(@RequestParam(value = "playlistName") String playlistName, Model model, HttpServletRequest req, HttpSession session)
			throws IllegalInputException, DataBaseException {

		int channelId = (int) session.getAttribute("channelId");
		playlistDao.createNewPlaylist(playlistName, channelId);
		List<ChannelPlaylistDTO> playlists = playlistDao.getAllChannelPlaylists(channelId);
		Map<String,List<ChannelPlaylistDTO>> result = new HashMap<>();
		result.put("playlists",playlists );
		return result;
	}
	
	@RequestMapping(value = "/deletePlaylist ", method = RequestMethod.DELETE)
	public String deletePlaylist(@RequestParam(value = "playlistId", required = true) int playlistId,
			HttpSession session) throws IllegalInputException, DataBaseException {

		playlistDao.deletePlaylist(playlistId);
		return "";
	}

	@RequestMapping(value = "/removeVideoFromPlaylist ", method = RequestMethod.POST)
	public String deleteVideoFromPlaylist(@RequestParam(value = "playlistId", required = true) int playlistId,
			@RequestParam(value = "videoId", required = true) int videoId)
			throws IllegalInputException, DataBaseException {

		videoDao.deleteVideoFromPlaylist(videoId, playlistId);
		return "";
	}
	
}
