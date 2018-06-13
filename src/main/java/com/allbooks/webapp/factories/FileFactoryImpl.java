package com.allbooks.webapp.factories;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileFactoryImpl extends FileFactory{

	@Override
	public byte[] fileToBytes(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	}

	@Override
	public File multipartFileGetOriginalFileName(MultipartFile multipartFile) {
		return new File(multipartFile.getOriginalFilename());
	}

	@Override
	public FileOutputStream createFileOutputStream(File file) throws FileNotFoundException {
		return new FileOutputStream(file);
	}

	@Override
	public InputStream getByteArrayInputStream(byte[] bookPicBytes) {
		return new ByteArrayInputStream(bookPicBytes);
	}

	@Override
	public BufferedImage getBufferedImage(InputStream in) throws IOException {
		return ImageIO.read(in);
	}

	@Override
	public ByteArrayOutputStream getByteArrayOutputStreamFromBufferedImage(BufferedImage resizedBookPic) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedBookPic, "jpg", baos);
		
		return baos;
	}

}
