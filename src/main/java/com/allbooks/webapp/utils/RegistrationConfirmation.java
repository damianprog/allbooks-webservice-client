package com.allbooks.webapp.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ReaderService;

@Component
public class RegistrationConfirmation {

	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private TokenVerification tokenVerification;
	
	public Map<String,Boolean> verifyConfirmation(int readerId,String token) {
		
		Map<String,Boolean> map = new HashMap<>();
		map.put("success", false);
		map.put("alreadyDone", false);
		
		Reader reader = readerService.getReaderById(readerId);

		if (reader == null) {
			return map;
		}

		if (reader.isEnabled()) {
			map.put("alreadyDone", true);
			return map;
		}

		map.put("success",tokenVerification.verifyToken(reader, token));
		
		return map;
		
	}
	
}
