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
import com.youtube.controller.upload.service.SecurityService;
import com.youtube.controller.upload.service.SignUpService;
import com.youtube.controller.upload.service.UploadService;
import com.youtube.model.dao.channel.IChannelDAO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.User;

@Controller
public class UserController {

	@Autowired
	private IChannelDAO channelDAO;
	
	@Autowired
	private SecurityService loginService;

	@Autowired
	private SignUpService signUpService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logIn(@RequestParam(value = "username", required = true) String username,
						@RequestParam(value = "password", required = true) String password, 
						HttpSession session, Model model) throws Exception {

		try {
			User user = loginService.login(username, password);
			Channel channel = channelDAO.getChannelByUserId(user.getUserId());
			session.setAttribute("channelId", channel.getChannelId());
			session.setAttribute("username", user.getUserName());
			session.setAttribute("photoUrl", user.getPhotoURL());
//			model.addAttribute("successMessage", "Log in successful.");
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/index";
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signOut(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/index";
	}
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestParam(value = "username", required = true) String username,
						 @RequestParam(value = "password", required = true) String password,
						 @RequestParam(value = "email", required = true) String email, 
						 HttpSession session, Model model) throws Exception {

		try {
			User user = signUpService.register(username, password, email);
			Channel channel = channelDAO.getChannelByUserId(user.getUserId());
			session.setAttribute("channelId", channel.getChannelId());
			session.setAttribute("username", user.getUserName());
			session.setAttribute("photoUrl", user.getPhotoURL());
			model.addAttribute("successMessage", "Sign Up successful.");
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@RequestParam(value = "newPassword", required = true) String newPassword,
								 @RequestParam(value = "oldPassword", required = true) String oldPassword,
								 HttpSession session, Model model) throws Exception {

		try {
			String username = session.getAttribute("username").toString();
			loginService.changePassword(username, oldPassword, newPassword);
			model.addAttribute("successMessage", "Password changes successfully. Please log in again with your new password.");
			session.invalidate();
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/index";
	}
	
	
}
