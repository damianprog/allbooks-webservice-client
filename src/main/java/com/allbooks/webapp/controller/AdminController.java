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
import com.allbooks.webapp.utils.ControllerUtils;

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
			@RequestParam("authorPhotoTemp") MultipartFile mfAuthorPhoto, @ModelAttribute("book") Book book,
			Model theModel) throws IOException {

		byte[] bookPhotoBytes = ControllerUtils.convertMultipartImage(mfBookPhoto, 150, 228);

		byte[] authorPhotoBytes = ControllerUtils.convertMultipartImage(mfAuthorPhoto, 50, 66);

		book.setBookPhoto(bookPhotoBytes);
		book.setAuthorPhoto(authorPhotoBytes);

		readerService.saveBook(book);

		theModel.addAttribute("book", book);

		return "addbook";
	}

}
