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

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.UploadService;
import com.youtube.model.pojo.User;

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
			// String realPath= "D://";
			uploadService.addVideo(file, title, description, (int) session.getAttribute("channelId"), realPath);
		} catch (IllegalInputException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "upload";
		}
		// add here session.getChannelID
		return "redirect:/profile?channelId=" + session.getAttribute("channelId");
	}

	@RequestMapping(value = "/changeProfilePic", method = RequestMethod.POST)
	public String changeProfilePic(@RequestParam(name = "profile_photo", required = false) MultipartFile file,
			HttpSession session, Model model) throws Exception {

		try {
			final String username = session.getAttribute("username").toString();
			String photoUrl;
			
			final String realPath = session.getServletContext().getRealPath("/static/");
			if (file == null) {
				User user = uploadService.setDefaultProfilePicture(file, realPath, username);
				photoUrl = user.getPhotoURL();
			}else {
				User user = uploadService.changeProfilePicture(file, realPath, username);
				photoUrl = user.getPhotoURL();
			}
			session.setAttribute("photoUrl", photoUrl);
			return "redirect:/profile?channelId=" + session.getAttribute("channelId");
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/error";
		}
	}

}
