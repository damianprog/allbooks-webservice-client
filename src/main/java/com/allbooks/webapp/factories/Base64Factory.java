package com.allbooks.webapp.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public abstract class Base64Factory {

	public abstract byte[] encode(byte[] theEncodedBase64);

	public abstract String createStringFromBytes(byte[] encodeBase64);
	
}
