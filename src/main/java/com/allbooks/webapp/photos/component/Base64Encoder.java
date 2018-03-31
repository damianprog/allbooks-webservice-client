package com.allbooks.webapp.photos.component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64Encoder {
	
	public String getEncodedImage(byte[] theEncodedBase64) {

		String base64Encoded = null;

		byte[] encodeBase64 = Base64.getEncoder().encode(theEncodedBase64);

		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return base64Encoded;
	}
	
}
