package com.allbooks.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.allbooks.webapp.enumeration.Information;
import com.allbooks.webapp.exceptions.entity.AccessForbidden;
import com.allbooks.webapp.exceptions.entity.ExpiredInvitationToken;
import com.allbooks.webapp.exceptions.entity.NotFoundException;

@ControllerAdvice
public class ExceptionsController {

	@ExceptionHandler(AccessForbidden.class)
	public String accessForbidden(Model theModel) {

		theModel.addAttribute("information",Information.ACCESS_DENIED);
		
		return "information";
	}

	@ExceptionHandler(NotFoundException.class)
	public String notFoundException(Model theModel) {

		theModel.addAttribute("information",Information.NOT_FOUND);
		
		return "information";
	}
	
	@ExceptionHandler(ExpiredInvitationToken.class)
	public String expiredInvitationToken() {
		return "redirect:/reader/join";
	}
	
}
