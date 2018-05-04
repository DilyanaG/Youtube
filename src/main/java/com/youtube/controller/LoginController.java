package com.youtube.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtube.controller.exceptions.DataBaseException;
import com.youtube.controller.exceptions.IllegalInputException;
import com.youtube.model.dao.user.UserDAO;
import com.youtube.model.pojo.User;

@Controller
public class LoginController {
   
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public String login(Model model,HttpServletRequest req) {
	   String username=(String) req.getParameter("username");
	   String password=(String) req.getParameter("password");
	   System.out.println(username+"   "+password);
	try {
		new UserDAO().loginUser(new User(username, password,""));
	} catch (IllegalInputException | DataBaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//here call user dao for checkuser if user dont exxist put error message in model
	if(Math.random()<0.2){
		model.addAttribute("errorMessage","your username or password not correct please try again");
	}else{
		 HttpSession session = req.getSession();
		    session.setAttribute("channelId", 2);
			model.addAttribute("successMessage","Login uraaa");
				
	}
	  
	   	return "redirect:index";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/sigout")
	public String signOut(Model model,HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
	   return "redirect:index";
	}
}
