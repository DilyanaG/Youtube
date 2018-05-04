package com.youtube.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.VideoDAO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Comment;
import com.youtube.model.pojo.User;
import com.youtube.model.pojo.Video;

@RestController
public class VideoController {

	
	private static final int MAX_VIDEOS_FOR_PAGE = 15;
	private VideoDAO videoDao= VideoDAO.getInstance();
	
	@RequestMapping(value = "/videoLoader", method = RequestMethod.GET)
	public Map<String, List<VideoTopViewDTO>> doGet() throws IllegalInputException, DataBaseException {
//		System.out.println(req.getParameter("parametyr"));
//		String action = req.getParameter("parametyr");
//        System.out.println(2);
//		
//		switch ("MOST") {
//		case "MOST":
//            videos=videoDao.getMostPopularVideos();
//			break;
//		case "RECENT":
//             videos=videoDao.getRecentVideos();
//			break;
//		default:
//		      videos=videoDao.getVideosByTagAndSortByDate(null);	
//			break;
//		}
		List<Video> videos = new ArrayList<>();
		for (int i = 0; i <MAX_VIDEOS_FOR_PAGE; i++) {
			videos.add(new Video(1, new Channel(i+1,new User("goshkata", "1234567", "gosho@abv.bg")), "video"+i, "photo"+i, "title"+i,LocalDateTime.now(), "description"+i, new Random().nextInt(50), 0,0));
		}
		
      System.out.println(videos.size());
	List<VideoTopViewDTO> sendVideos = new ArrayList<>();
	//fill videoTopViewDTO 
		for (int i = 0; i <MAX_VIDEOS_FOR_PAGE &&i<videos.size(); i++) {
			
	
			sendVideos.add(new VideoTopViewDTO (videos.get(i).getVideoId(),
					                            videos.get(i).getChannel().getChannelId(),
					                            videos.get(i).getTitle(),
					                            videos.get(i).getPhotoUrl(),
					                            videos.get(i).getViews()
					                            ));
		}
		Map<String, List<VideoTopViewDTO>> result = new HashMap<String, List<VideoTopViewDTO>>();

		result.put("videos", sendVideos);
		return result;
	}
}
