package com.youtube.controller;

import java.util.ArrayList;
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
import com.youtube.model.dao.playlist.PlaylistDAO;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.playlist.ChannelPlaylistDTO;
import com.youtube.model.dto.playlist.PlaylistTopViewDTO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Video;

@Controller
public class SingleMenuController {
	
	@Autowired
	private PlaylistDAO playlistDAO;
	@Autowired
	private IVideoDAO videoDAO;
	@Autowired
	private VideoService videoService;

	@RequestMapping(method = RequestMethod.GET, value = "/playlist")
	public String singlePlaylist(Model model, 
			              @RequestParam(value = "playlistId") int playlistId,
			              HttpSession session) throws IllegalInputException, DataBaseException {
		
		List<Video> playlistVideos= videoDAO.getAllVideosFromPlaylist(playlistId);
		if(playlistVideos!=null&& !playlistVideos.isEmpty()){
			  VideoDTO currentVideo=videoService.playVideo(playlistVideos.get(0).getVideoId());
			    playlistVideos.remove(playlistVideos.get(0));
					
			    
			    List<VideoTopViewDTO> otherVideos= new ArrayList<>();
			     for(Video video:playlistVideos){
			    	 otherVideos.add(new VideoTopViewDTO(video));
			     }
			     if(session.getAttribute("channelId")!=null){
			      List<ChannelPlaylistDTO> playlists = playlistDAO.getAllChannelPlaylists((int)session.getAttribute("channelId")); 
			         for(ChannelPlaylistDTO c: playlists){
			        	 if(c.getPlaylistId()==playlistId){
			        		 model.addAttribute("logged", "true");
			        	 }
			         }
			     }
			     model.addAttribute("playlistId", playlistId);
			    model.addAttribute("currentVideo", currentVideo);
				model.addAttribute("playlistVideos", otherVideos);
		};
	  
		
		
		return "singlePlaylist";
	}


}
