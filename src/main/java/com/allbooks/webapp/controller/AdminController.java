package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProfileService profileService;

	@Autowired
	ReaderService readerService;

	@GetMapping("/addBookPage")
	public String addBook(Model theModel) {

		Book book = new Book();

		theModel.addAttribute("book", book);

		return "addbook";
	}

	@PostMapping("/addBook")
	public String addBook(@RequestParam("bookPhotoTemp") MultipartFile mfBookPhoto,
			@RequestParam("authorPhotoTemp") MultipartFile mfAuthorPhoto, @ModelAttribute("book") Book book, Model theModel) {

		
		try {
			File convFile = convert(mfBookPhoto);
			File convFile2 = convert(mfAuthorPhoto);

			BufferedImage bookPhotoBimg = ImageIO.read(convFile);
			BufferedImage authorPhotoBimg = ImageIO.read(convFile2);

			BufferedImage resized = resize(bookPhotoBimg, 150, 228);
			BufferedImage resized2 = resize(authorPhotoBimg, 50, 66);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resized, "jpg", baos);
			byte[] bookPhotoBytes = baos.toByteArray();
			
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			ImageIO.write(resized2, "jpg", baos2);
			byte[] authorPhotoBytes = baos2.toByteArray();
			
			book.setBookPhoto(bookPhotoBytes);
			book.setAuthorPhoto(authorPhotoBytes);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		readerService.saveBook(book);

		theModel.addAttribute("book", book);

		return "addbook";
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
