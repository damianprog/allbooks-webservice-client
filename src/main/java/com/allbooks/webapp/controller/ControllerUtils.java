package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class ControllerUtils {

	public static byte[] convertMultipartImage(MultipartFile mf,int width,int height) throws IOException {
		
		File convFile = convert(mf);

		BufferedImage bimg = ImageIO.read(convFile);

		BufferedImage resized = resize(bimg, width, height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resized, "jpg", baos);
		byte[] bytes = baos.toByteArray();
		
		return bytes;
		
	}
	
	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		return Thumbnails.of(img).size(newW, newH).asBufferedImage();
	}
	
}
