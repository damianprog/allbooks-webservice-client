package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.BookActionDataObjectFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.readerbook.ReaderBookSaver;
import com.allbooks.webapp.utils.readerbook.ReaderBooksForMyBooksGetter;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;
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
	private ReaderBookSaver readerBookSaver;

	@Autowired
	private ReaderBooksForMyBooksGetter readerBooksForMyBooksGetter;

	@Autowired
	private BookActionDataObjectFactory bookActionDataObjectFactory;

	@Autowired
	private SaveService saveService;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam("readerId") int readerId, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "shelves", defaultValue = "All") String shelves, Model theModel, HttpSession session,
			Principal principal) {

		Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService.getReaderBooksQuantities(readerId);

		ShelvesState shelvesStates = ShelvesState.enumValueOf(shelves);

		Page<ReaderBook> readerBooksPage = readerBooksForMyBooksGetter.getPreparedReaderBooks(readerId, shelvesStates,
				page);

		theModel.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		theModel.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		theModel.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
		theModel.addAttribute("all", readerBooksQuantitiesMap.get("all"));
		theModel.addAttribute("readerBooks", readerBooksPage.getContent());
		theModel.addAttribute("readerLogin", readerService.getReaderById(readerId).getUsername());
		theModel.addAttribute("readerId", readerId);
		theModel.addAttribute("shelvesStates", shelvesStates);
		theModel.addAttribute("readerBooksPage", readerBooksPage);
		theModel.addAttribute("page", page);

		return "mybooks";
	}

	@PostMapping("/updateState")
	public String updateState(@RequestParam("newShelves") String newShelves,
			@RequestParam("readerBookId") int readerBookId, @RequestParam("bookId") int bookId,
			@RequestParam("isItUpdateReaderBook") boolean isItUpdateReaderBook, HttpSession session, Model theModel,
			Principal principal, RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());

		ReaderBook readerBook = readerBookService.getReaderBookById(readerBookId);

		readerBook.setShelvesStates(ShelvesState.enumValueOf(newShelves));

		ReaderBookData readerBookData = bookActionDataObjectFactory.createReaderBookData(readerBook, bookId,
				isItUpdateReaderBook);

		readerBookSaver.save(readerBookData);

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/myBooks/showMyBooks";

	}

	@PostMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) throws ParseException {

		Date thedate = new SimpleDateFormat("yyyy-MM-dd").parse(dateRead);

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		readerBookService.saveReadDate(thedate, bookId, reader.getId());

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

	@PostMapping("/saveReaderBook")
	public String readState(@RequestParam(value = "readerBookId", required = false) Integer readerBookId,
			@RequestParam("bookId") int bookId, @RequestParam("shelves") String shelves,
			@RequestParam("isItUpdateReaderBook") boolean isItUpdateReaderBook, HttpSession session,
			Principal principal, RedirectAttributes ra) throws IOException {

		ReaderBook readerBook;

		if (isItUpdateReaderBook)
			readerBook = readerBookService.getReaderBookById(readerBookId);
		else
			readerBook = new ReaderBook();

		readerBook.setShelvesStates(ShelvesState.enumValueOf(shelves));

		saveService.saveReaderBook(
				bookActionDataObjectFactory.createReaderBookData(readerBook, bookId, isItUpdateReaderBook));

		ra.addAttribute("bookId", bookId);

		return "redirect:/reader/showBook";
	}

}
