package com.allbooks.webapp.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	private HttpServletRequest request;

	public int getLoggedReaderId() {

		if(session.getAttribute("readerId") != null)
			return (int) session.getAttribute("readerId");
		else
			return 0;
		
	}

	public String getLoggedReaderUserName() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getName();
	}

	public boolean isReaderAuthenticated() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth instanceof AnonymousAuthenticationToken)
			return false;
		else
			return true;

	}

	public void autologin(String username, String password) {
		try {
			request.login(username, password);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
