package com.youtube.controller.rest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.playlist.IPlaylistDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;

@RestController
public class PlaylistController {
 
	@Autowired
	private IPlaylistDAO playlistDao;
	@Autowired
	private IVideoDAO videoDao;
	
	
	@RequestMapping(value = "/playlists", method = RequestMethod.GET)
	public Map<String, List<Object>> doGet(HttpServletRequest req) throws DataBaseException, IllegalInputException {
    
		int channelId=Integer.valueOf(req.getParameter("channelId"));
	    
		List<Object> sendPlaylist= new ArrayList<>();
		//get playlists for channel
		List<Playlist> playlists = playlistDao.getPlaylistsByChannelAndSortByCreationDate(channelId);
	      //create PlaylistTopViewDTO's for playlists and add in list 
		for(Playlist playlist: playlists ){
			List<Video> playlistVideos = videoDao.getAllVideosFromPlaylist(playlist);
			sendPlaylist.add(new PlaylistTopViewDTO(playlist,playlistVideos));
		}
		System.out.println(sendPlaylist);
	         	
		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
        result.put("playlists", sendPlaylist);
		return result;
	}
	
	
	
	@RequestMapping(value = "/changeVideo", method = RequestMethod.GET)
	public Map<String, List<Object>> getVideoByID(HttpServletRequest req) {
		System.out.println(req.getParameter("videoId"));
		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
		
		List<Object> videos = new ArrayList<>();
		List<Object> comments = new ArrayList<>();
		List<Object> currentVideo = new ArrayList<>();
		Video video = new Video();
		video.setVideoId(333);
		currentVideo.add(video);
		for(int i=0;i<10;i++){
			Video video1 = new Video();
			video1.setVideoId(i);
			videos.add(video1);
			comments.add(new Comment());
		}
		result.put("playlistVideos",videos);
		result.put("comments",comments);
		result.put("currentVideo",currentVideo);
		return result;
	}
	
	@RequestMapping(value = "/deletePlaylist ", method = RequestMethod.DELETE)
	public String  deleteVideo(@RequestParam(value = "playlistId", required = true) int playlistId,
			                       HttpSession session) throws IllegalInputException, DataBaseException{
		
		playlistDao.deletePlaylist(playlistId);
		return "";
	}
}
