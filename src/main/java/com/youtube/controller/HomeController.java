package com.youtube.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String homePage(Model model, HttpServletRequest req) {
		if (req.getParameter("errorMessage") != null) {
			model.addAttribute("errorMessage", req.getParameter("errorMessage"));
		}
		if (req.getParameter("successMessage") != null) {
			model.addAttribute("successMessage", req.getParameter("successMessage"));
		}
		return "index";
	}

	@RequestMapping(value = { "/error", "/upload/error" }, method = RequestMethod.GET)
	public String error(Model model) {
		return "error";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model, HttpServletRequest req) {

		req.getSession().setAttribute("currentProfile", req.getParameter("channelId"));
		System.out.println(req.getParameter(req.getParameter("channelId")));

		String[] videos = { "video", "video", "video", "video", "video", "video", "video",

		};
		model.addAttribute("videos", videos);

		return "profile";
	}

	@RequestMapping(value = { "/videos/index" }, method = RequestMethod.GET)
	public String sayHello1() {
		return "redirect:/index";
	}

}
