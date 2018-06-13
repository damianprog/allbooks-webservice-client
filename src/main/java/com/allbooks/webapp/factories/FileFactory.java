package com.allbooks.webapp.factories;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public abstract class FileFactory {

	public abstract byte[] fileToBytes(File file) throws IOException;

	public abstract File multipartFileGetOriginalFileName(MultipartFile multipartFile);

	public abstract FileOutputStream createFileOutputStream(File file) throws FileNotFoundException;

	public abstract InputStream getByteArrayInputStream(byte[] bookPicBytes);

	public abstract BufferedImage getBufferedImage(InputStream inputStream) throws IOException;

	public abstract ByteArrayOutputStream getByteArrayOutputStreamFromBufferedImage(BufferedImage resizedBookPic)
			throws IOException;

}
