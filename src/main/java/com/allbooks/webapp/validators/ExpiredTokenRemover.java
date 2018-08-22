package com.allbooks.webapp.validators;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.service.TokenService;

@Component
public class ExpiredTokenRemover {

	@Autowired
	private TokenService tokenService;
	
	public boolean isTokenExpired(Token token) {
		
		Calendar cal = Calendar.getInstance();

		if((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			tokenService.deleteTokenById(token.getId());
			return true;
		}else
			return false;
		
	}
	
}
