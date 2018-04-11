package com.allbooks.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProfileService profileService;

	@Autowired
	BookService bookService;

	@Autowired
	ReaderService readerService;

	@Autowired
	PhotoServiceImpl photoService;

	@GetMapping("/addBookPage")
	public String addBook(Model theModel) {

		theModel.addAttribute("book", new Book());

		return "addbook";
	}

	@PostMapping("/addBook")
	public String addBook(@RequestParam("bookPhotoTemp") MultipartFile mfBookPhoto,
			@RequestParam("authorPhotoTemp") MultipartFile mfAuthorPhoto, @ModelAttribute("book") Book book,
			Model theModel) throws IOException {

		book.setBookPhoto(photoService.convertMultipartImage(mfBookPhoto, 150, 228));
		book.setAuthorPhoto(photoService.convertMultipartImage(mfAuthorPhoto, 50, 66));

		bookService.saveBook(book);

		theModel.addAttribute("book", book);

		return "addbook";
	}

}
