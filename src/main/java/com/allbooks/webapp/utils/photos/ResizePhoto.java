package com.allbooks.webapp.utils.photos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.factories.FileFactory;

@Component
public class ResizePhoto {

	@Autowired
	private ThumbnailsResize thumbnailsResize;
	
	@Autowired
	private FileFactory fileFactory;
	
	public byte[] resize(byte[] bookPicBytes, int width, int height) {

		InputStream inputStream = fileFactory.getByteArrayInputStream(bookPicBytes);
		byte[] bookPic = {};

		try {

			BufferedImage bufferedImageToResize = fileFactory.getBufferedImage(inputStream);

			BufferedImage resizedBufferedImage = thumbnailsResize.resize(bufferedImageToResize, width, height);

			ByteArrayOutputStream byteArrayOutputStream = fileFactory.getByteArrayOutputStreamFromBufferedImage(resizedBufferedImage);
			
			bookPic = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bookPic;
	}

}
