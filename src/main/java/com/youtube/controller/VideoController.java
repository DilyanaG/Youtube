package com.youtube.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.VideoService;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoDTO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Video;

@Controller
public class VideoController {

	private static final int MAX_VIDEOS_FOR_PAGE = 15;
	
	@Autowired
	private IVideoDAO videoDAO;
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value = "/videoLoader", method = RequestMethod.GET)
	public Map<String, List<VideoTopViewDTO>> doGet(HttpServletRequest req) throws IllegalInputException, DataBaseException {

		List<Video> videos = new ArrayList<>();
		
		String action = req.getParameter("parametyr");
		System.out.println(req.getParameter("parametyr"));
        System.out.println(2);
		
		switch (action) {
		case "MOST":
            videos=videoDAO.getMostPopularVideos();
			break;
		case "RECENT":
             videos=videoDAO.getRecentVideos();
			break;
		default:
		      videos=videoDAO.getVideosByTagAndSortByDate(null);	
			break;
		}
//		List<Video> videos = new ArrayList<>();
//		for (int i = 0; i <MAX_VIDEOS_FOR_PAGE; i++) {
//			videos.add(new Video(1, new Channel(i+1,new User("goshkata", "1234567", "gosho@abv.bg")), "video"+i, "photo"+i, "title"+i,LocalDateTime.now(), "description"+i, new Random().nextInt(50), 0,0));
//		}
		
      System.out.println("videos size"+videos.size());
	List<VideoTopViewDTO> sendVideos = new ArrayList<>();
	//fill videoTopViewDTO 
		for (int i = 0; i <MAX_VIDEOS_FOR_PAGE &&i<videos.size(); i++) {
			
	        System.out.println(videos.get(i));
			sendVideos.add(new VideoTopViewDTO(videos.get(i)));
		}
		Map<String, List<VideoTopViewDTO>> result = new HashMap<String, List<VideoTopViewDTO>>();

		result.put("videos", sendVideos);
		return result;
	}
	
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public String playVideo(Model model, HttpServletRequest req) throws Exception {

		int videoId = Integer.valueOf(req.getParameter("videoId"));
		VideoDTO video = videoService.playVideo(videoId);

		model.addAttribute("video", video);
		return "single";
	}
}
