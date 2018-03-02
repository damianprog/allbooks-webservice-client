package com.allbooks.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Reader;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error",required=false) Boolean error,Model theModel) {
		
		Reader reader = new Reader();
		theModel.addAttribute("reader",reader);
		
		if((error != null)&&(error == true))
			theModel.addAttribute("error",true);
		
		return "login";
	}
	
}
