package com.youtube.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HelloControllerrr {
  
	@RequestMapping(value={"/error","/upload/error"},method = RequestMethod.GET)
	public String error(Model model){
		return "error";
	}
	
	
	@RequestMapping(value="/profile",method = RequestMethod.GET)
	public String profile(Model model){
		 String[] videos={
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 
		 };
		model.addAttribute("videos",videos);
		
		return "profile";
	}
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String sayHello(Model model){
		 String[] videos={
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 "video",
				 
		 };
		model.addAttribute("videos",videos);
		return "index";
}	
	@RequestMapping(value="/videos/index",method = RequestMethod.GET)
	public String sayHello1(){
		return "redirect:/index";
}
	@RequestMapping(method=RequestMethod.GET, value="/videos/{id}")
	public String viewPatka(Model model, @PathVariable Integer id) throws Exception  {
		
		model.addAttribute(id);
		throw new Exception();
		//return "single";
	}

}
