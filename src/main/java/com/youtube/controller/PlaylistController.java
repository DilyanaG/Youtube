package com.youtube.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.model.dao.playlist.IPlaylistDAO;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.Playlist;
import com.youtube.model.pojo.Video;

@RestController
public class PlaylistController {
 
	@Autowired
	private IPlaylistDAO playlistDao;
	
	@RequestMapping(value = "/playlists", method = RequestMethod.GET)
	public Map<String, List<Object>> doGet(HttpServletRequest req) throws DataBaseException {
//	
//		?userId={id}  get parametyr userId in profile 
//		or maybe save current oppenned profile in session 
//	   put all playlist  for profile 
//		 playlist has 
//		            playlistId
//		            playlistName
//		            pictureForPlaylist(last addet video picture)
//		  create   DTO for this PlaylistViewDTO      
		int channelId=Integer.valueOf(req.getParameter("channelId"));
	    List<Playlist> playlists = playlistDao.getPlaylistsByChannelAndSortByCreationDate(channelId);
		System.out.println(playlists);
		
//		
		Map<String, List<Object>> result = new HashMap<String, List<Object>>();
//		List<Object> playlists = new ArrayList<>();
//		List<Object> comments = new ArrayList<>();
//		for(int i=0;i<10;i++){
//			playlists.add(new Playlist(i,null,"playlist"+i));
//			comments.add(new Comment(i, null, "content"+i,null));
//		}
//		result.put("playlists",playlists);
//		result.put("comments",comments);
      
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
}
