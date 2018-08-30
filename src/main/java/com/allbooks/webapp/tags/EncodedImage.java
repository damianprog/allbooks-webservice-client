package com.allbooks.webapp.tags;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.coobird.thumbnailator.Thumbnails;

public class EncodedImage extends SimpleTagSupport {

	private byte[] image;

	private int width;

	private int height;

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("tag lib");
		try {
			JspWriter out = getJspContext().getOut();
			if (image != null && image.length > 0) {
				byte[] resizedPictureBytes = resize(image, width, height);
				byte[] encodeBase64 = Base64.getEncoder().encode(resizedPictureBytes);
				String base64Encoded = new String(encodeBase64, "UTF-8");
				out.print("data:image/jpeg;base64," + base64Encoded);

			}
		} catch (Exception e) {
			throw new JspException("Error: " + e.getMessage());
		}
	}

	public byte[] resize(byte[] bookPicBytes, int width, int height) {

		InputStream inputStream = new ByteArrayInputStream(bookPicBytes);
		byte[] bookPic = {};

		try {

			BufferedImage bufferedImageToResize = ImageIO.read(inputStream);

			BufferedImage resizedBufferedImage = Thumbnails.of(bufferedImageToResize).size(width, height).asBufferedImage();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(resizedBufferedImage, "jpg", baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			bookPic = baos.toByteArray();
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
