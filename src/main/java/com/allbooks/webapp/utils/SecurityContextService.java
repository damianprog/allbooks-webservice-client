package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

	@Autowired
	private HttpSession session;
	
	public int getLoggedReaderId() {
		
		return (int) session.getAttribute("readerId");
		
	}
	
	public String getLoggedReaderUserName() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getName();
	}
	
	public boolean isReaderAuthenticated() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth instanceof AnonymousAuthenticationToken)
			return false;
		else 
			return true;
		
	}
	
}
