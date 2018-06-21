package com.allbooks.webapp.utils.photos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.factories.Base64Factory;

@Component
public class Base64Encoder {

	@Autowired
	private Base64Factory base64Factory;

	public String getEncodedImage(byte[] base64ToEncode) {

		byte[] encodedBase64 = base64Factory.encode(base64ToEncode);

		return  base64Factory.createStringFromBytes(encodedBase64);

	}
}

