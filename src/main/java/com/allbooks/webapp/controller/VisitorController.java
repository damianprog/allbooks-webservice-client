package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.utils.model.BookModelCreator;
import com.allbooks.webapp.utils.model.MainPageModelCreator;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/visitor")
public class VisitorController {

	@Autowired
	private BookService bookService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private MainPageModelCreator mainPageModelCreator;

	@Autowired
	private BookModelCreator bookModelCreator;

	@GetMapping("/showBook")
	public String showBook(@RequestParam(value = "bookId", required = false) int bookId, Model theModel,
			HttpSession session, Principal principal) throws Exception {

		theModel.addAllAttributes(bookModelCreator.create(bookId));

		return "book/book";
	}

	@GetMapping("/main")
	public String mainPage(Model theModel, HttpServletRequest request) {

		ModelMap modelMap = mainPageModelCreator.createModel();

		theModel.addAllAttributes(modelMap);

		if ((boolean) modelMap.get("isAuthenticated"))
			return "reader/loggedReaderMain";
		else
			return "main";

	}

	@GetMapping("/login")
	public String loginPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "account/login";
	}

	@GetMapping("/showCategory")
	public String showCategory(@RequestParam("categoryName") String category,
			@RequestParam(defaultValue = "1") int page, Model theModel) throws IOException {

		Page<Book> booksPage = bookService.getBooksByCategory(category, page);

		theModel.addAttribute("books", photoService.encodeBookPics(booksPage.getContent(), 115, 180));
		theModel.addAttribute("booksPage", booksPage);
		theModel.addAttribute("currentPage", page);
		theModel.addAttribute("category", category);

		return "book/categories";
	}

	@GetMapping("/searchBooks")
	public String searchBooks(Model theModel, @RequestParam(value = "phrase", required = false) String phrase)
			throws IOException {

		if (phrase != null)
			theModel.addAttribute("books", photoService.encodeBookPics(bookService.getBooksByPhrase(phrase), 115, 180));

		return "book/searchBooks";
	}

}
