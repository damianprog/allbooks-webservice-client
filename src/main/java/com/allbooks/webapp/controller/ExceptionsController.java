package com.allbooks.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.allbooks.webapp.exceptions.entity.AccessForbidden;
import com.allbooks.webapp.exceptions.entity.NotFoundException;

@ControllerAdvice
public class ExceptionsController {

	@ExceptionHandler(AccessForbidden.class)
	public String accessForbidden(Model theModel) {

		return "accessDenied";
	}

	@ExceptionHandler(NotFoundException.class)
	public String notFoundException(Model theModel) {

		return "notFound";
	}
	
}
