package com.youtube.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.SignUpService;

@Controller
public class SignUpController {

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signUp(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "email", required = true) String email, HttpSession session, Model model)
			throws Exception {

		System.out.println(username);
		System.out.println(password);
		System.out.println(email);

		try {
			new SignUpService().register(username, password, email);
			model.addAttribute("successMessage", "Sign Up successful. Please log in.");
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/index";
	}

}
