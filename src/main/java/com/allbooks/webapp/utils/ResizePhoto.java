package com.allbooks.webapp.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ResizePhoto {

	public static byte[] resize(byte[] bookPicBytes,int width,int height) throws IOException {
		
		InputStream in = new ByteArrayInputStream(bookPicBytes);
		BufferedImage imgToResize = ImageIO.read(in);
		BufferedImage resizedBookPic = resize(imgToResize, width, height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedBookPic, "jpg", baos);
		
		byte[] bookPic = baos.toByteArray();
		
		return bookPic;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		return Thumbnails.of(img).size(newW, newH).asBufferedImage();
	}
	
}
