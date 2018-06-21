package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.factories.BookActionDataObjectFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.readerbook.ReaderBookSaver;
import com.allbooks.webapp.utils.readerbook.ReaderBooksForMyBooksGetter;

@Controller
@RequestMapping("/myBooks")
public class MyBooksController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReaderBookSaver readerBookSaver;

	@Autowired
	private ReaderBooksForMyBooksGetter readerBooksForMyBooksGetter;

	@Autowired
	private BookActionDataObjectFactory bookActionDataObjectFactory;

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam(value = "readerId", required = false) Integer readerId, Model theModel,
			HttpSession session, Principal principal) {

		theModel.addAttribute("readerBooks", readerBooksForMyBooksGetter.getPreparedReaderBooks(readerId));
		theModel.addAttribute("readerLogin", readerService.getReaderById(readerId).getUsername());

		return "mybooks";
	}

	@PostMapping("/updateState")
	public String updateState(@RequestParam("newShelves") String newShelves,
			@RequestParam("readerBookId") int readerBookId, @RequestParam("bookId") int bookId,
			@RequestParam("isItUpdateReaderBook") boolean isItUpdateReaderBook, HttpSession session, Model theModel,
			Principal principal, RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());

		ReaderBook readerBook = readerBookService.getReaderBookById(readerBookId);
		readerBook.setShelves(newShelves);

		ReaderBookData readerBookData = bookActionDataObjectFactory.createReaderBookData(readerBook, bookId,
				isItUpdateReaderBook);

		readerBookSaver.save(readerBookData);

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/myBooks/showMyBooks";

	}

	@PostMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		readerBookService.saveReadDate(dateRead, bookId, reader.getId());

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/myBooks/showMyBooks";
	}

	@GetMapping("/deleteReaderBook")
	public String deleteReaderBook(@RequestParam("bookId") int bookId, Model theModel, RedirectAttributes ra,
			Principal principal) {

		readerBookService.deleteReaderBookById(bookId, principal.getName());

		ra.addAttribute("readerId", readerService.getReaderByUsername(principal.getName()).getId());

		return "redirect:/myBooks/showMyBooks";
	}

}
