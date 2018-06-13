package com.allbooks.webapp.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

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
