package com.allbooks.webapp.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;

@Controller
@RequestMapping("/myBooks")
public class MyBooksController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReaderBookService readerBookService;
	
	@Autowired
	private ReaderService readerService;
	
	@GetMapping("/updateState")
	public String updateState(@RequestParam("bookName") String bookName, @RequestParam("newState") String newState,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		readerBookService.updateReaderBookShelves(newState, bookId, reader.getId());

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/reader/showMyBooks";

	}

	@GetMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		readerBookService.saveReadDate(dateRead, bookId, reader.getId());

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/reader/showMyBooks";
	}
	
	@GetMapping("/deleteReaderBook")
	public String deleteReaderBook(@RequestParam("bookId") int bookId, Model theModel, RedirectAttributes ra,
			Principal principal) {

		readerBookService.deleteReaderBookById(bookId, principal.getName());

		ra.addAttribute("readerId", readerService.getReaderByUsername(principal.getName()).getId());

		return "redirect:/reader/showMyBooks";
	}
	
}
