package com.youtube.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class HelloController {

	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String sayHello(Model model) {
		model.addAttribute("text", "Hi !");
		
		return "index";
	
	}	
	@RequestMapping(method=RequestMethod.GET, value="/videos/{id}")
	public String viewPatka(Model model, @PathVariable Integer id) {
		
		
		model.addAttribute(id);
		
		return "single";
	}

}
