package com.youtube.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.video.IVideoDAO;
import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Video;

@Controller
public class HomeController {

	private static final int MAX_VIDEOS_FOR_PAGE = 15;

	@Autowired
	private IVideoDAO videoDao;

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest req) throws IllegalInputException, DataBaseException {
		
		if (req.getParameter("errorMessage") != null) {
			model.addAttribute("errorMessage", req.getParameter("errorMessage"));
		}
		if (req.getParameter("successMessage") != null) {
			model.addAttribute("successMessage", req.getParameter("successMessage"));
		}
		List<Video> videos = new ArrayList<>();
		if (req.getParameter("search") == null) {

			videos = videoDao.getRecentVideos();
			model.addAttribute("message", "VIDEOS");
			System.out.println("*********************videos");
		} else {
			switch (req.getParameter("search")) {
			case "most":
				videos = videoDao.getMostPopularVideos();
				model.addAttribute("message", "MOST POPULAR VIDEOS");
				System.out.println("*********most videos");
				break;
			case "recent":
				videos = videoDao.getRecentVideos();
				model.addAttribute("message", "RECENT VIDEOS");
				System.out.println("********* recent videos");
				break;
			default:
				videos = videoDao.getVideosByTagAndSortByDate(req.getParameter("search"));
				model.addAttribute("message", "SEARCH RESULTS ");
				break;
			}

		}
		System.out.println("   --------videos size" + videos.size());
		List<VideoTopViewDTO> sendVideos = new ArrayList<>();
		// fill videoTopViewDTO
		for (int i = 0; i < MAX_VIDEOS_FOR_PAGE && i < videos.size(); i++) {

			System.out.println(videos.get(i));
			sendVideos.add(new VideoTopViewDTO(videos.get(i)));
		}

		model.addAttribute("videos", sendVideos);

		return "index";
	}

	@RequestMapping(value = { "/error", "/upload/error" }, method = RequestMethod.GET)
	public String error(Model model) {
		return "error";
	}

	@RequestMapping(value = { "/videos/index" }, method = RequestMethod.GET)
	public String sayHello1() {

		return "redirect:/index";
	}

}
