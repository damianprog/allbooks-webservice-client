package com.allbooks.webapp.utils.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.enumeration.VerificationTokenResponses;
import com.allbooks.webapp.service.ReaderService;

@Component
public class RegistrationConfirmation {

	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private TokenVerification tokenVerification;
	
	public Map<String,VerificationTokenResponses> verifyConfirmation(int readerId,String token) {
		
		Map<String,VerificationTokenResponses> map = new HashMap<>();
		
		Reader reader = readerService.getReaderById(readerId);

		if (reader == null) {
			map.put("info", VerificationTokenResponses.INVALID_TOKEN);
			return map;
		}

		if (reader.isEnabled()) {
			map.put("info", VerificationTokenResponses.ALREADY_AUTHENTICATED);
			return map;
		}

		map.put("info",tokenVerification.verifyToken(reader, token));
		
		return map;
		
	}
	
}
