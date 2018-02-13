package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

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
		
		theModel.addAttribute("book",book);
		
		return "addbook";
	}
	
	@PostMapping("/addBook")
	public String addBook(@ModelAttribute("book") Book book,Model theModel) {
		
		//readerService.saveBook(book);
		
		theModel.addAttribute("book",book);
		
		return "addbook";
	}
	
}
