package com.youtube.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.LogInService;
import com.youtube.model.dao.channel.ChannelDAO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.User;

@Controller
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logIn(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password, HttpSession session, Model model)
			throws Exception {

		System.out.println(username);
		System.out.println(password);

		try {
			User user = new LogInService().login(username, password);
			Channel channel = ChannelDAO.getInstance().getChannelByUserId(user.getUserId());
			session.setAttribute("channelId", channel.getChannelId());
			model.addAttribute("successMessage", "Log in successful.");
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sigout")
	public String signOut(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:index";
	}
}
