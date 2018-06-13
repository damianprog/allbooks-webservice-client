package com.allbooks.webapp.factories;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64FactoryImpl extends Base64Factory{

	@Override
	public byte[] encode(byte[] theEncodedBase64) {
		return Base64.getEncoder().encode(theEncodedBase64);
	}

	@Override
	public String createStringFromBytes(byte[] encodeBase64) {
		return new String(encodeBase64);
	}
	
}
