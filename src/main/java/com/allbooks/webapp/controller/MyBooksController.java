package com.allbooks.webapp.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.DataObjectFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.model.MyBooksModelCreator;
import com.allbooks.webapp.utils.service.SaveService;

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
	private DataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private MyBooksModelCreator myBooksModelCreator;

	@Autowired
	private SaveService saveService;
	
	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam("readerId") int readerId, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "shelves", defaultValue = "All") String shelves, Model theModel, HttpSession session,
			Principal principal) {

		theModel.addAllAttributes(myBooksModelCreator.create(readerId, page, shelves));

		return "reader/mybooks";
	}

	@PostMapping("/saveReaderBook")
	public String updateState(@RequestParam("shelves") String shelves,
			@RequestParam(value = "readerBookId", required = false) Integer readerBookId,
			@RequestParam(value = "pageName", required = false) String pageName, @RequestParam("bookId") int bookId,
			@RequestParam("isItUpdateReaderBook") boolean isItUpdateReaderBook, HttpSession session, Model theModel,
			RedirectAttributes ra)  {

		ReaderBookData readerBookData = bookActionDataObjectFactory
				.createReaderBookData(ShelvesState.enumValueOf(shelves), bookId);

		saveService.saveReaderBook(readerBookData);

		if(pageName.equals("book")) {
			ra.addAttribute("bookId", bookId);
			return "redirect:/visitor/showBook";
		}
		else {
			ra.addAttribute("readerId", contextService.getLoggedReaderId());
			return "redirect:/myBooks/showMyBooks";
		}

	}

	@PostMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) throws ParseException {

		Date thedate = new SimpleDateFormat("yyyy-MM-dd").parse(dateRead);

		int bookId = bookService.getBookId(bookName);
		int loggedReaderId = contextService.getLoggedReaderId();
		readerBookService.saveReadDate(thedate, bookId, loggedReaderId);

		ra.addAttribute("readerId", loggedReaderId);

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
