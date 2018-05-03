package com.youtube.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.controller.upload.service.SignUpService;

@Controller
public class SignUpController {

	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public String signUp(Model model, HttpServletRequest request) {
		
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		String email = (String) request.getParameter("email");
		
		SignUpService signUpService = new SignUpService();
		try {
			if(signUpService.register(username, password, email)){
				model.addAttribute("successMessage", "Sign Up successful. Please log in.");
			}
		} catch (IllegalInputException | DataBaseException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/index";
	}
}
