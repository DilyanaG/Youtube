package com.youtube.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = { "/upload", "/videos/upload" }, method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest req) {
		if (req.getSession().getAttribute("channelId") == null) {
			model.addAttribute("errorMessage", "PLEASE SIGN IN TO UPLOAD VIDEO!");
			return "index";
		}
		if (req.getParameter("errorMessage") != null) {
			model.addAttribute("errorMessage", req.getParameter("errorMessage"));
		}
		return "upload";
	}

	// upload video
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description, HttpSession session, Model model)
			throws Exception {
		try {
			final String realPath = session.getServletContext().getRealPath("/static/");
		//	String realPath= "D://";
			uploadService.addVideo(file, title, description, (int) session.getAttribute("channelId"), realPath);
		} catch (IllegalInputException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "upload";
		}
		// add here session.getChannelID
		return "redirect:/profile?channelId="+session.getAttribute("channelId");
	}

}
