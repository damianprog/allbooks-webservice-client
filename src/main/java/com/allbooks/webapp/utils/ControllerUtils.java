package com.allbooks.webapp.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class ControllerUtils {

	public static byte[] convertMultipartImage(MultipartFile mf, int width, int height) throws IOException {

		File convFile = MultipartToFile.convert(mf);

		return ResizePhoto.resize(Files.readAllBytes(convFile.toPath()), width, height);

	}

	public static String getEncodedImage(byte[] theEncodedBase64) {

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
