package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.bookactions.ReaderPostsWithPreparedReadersPhotoGetter;
import com.allbooks.webapp.utils.model.MainPageModelCreator;
import com.allbooks.webapp.utils.readerbook.ReaderBookAndRatingModelCreator;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;
import com.allbooks.webapp.utils.service.SaveService;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private SaveService saveService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter reviewsReaderPhotoPreparer;

	@Autowired
	private MainPageModelCreator mainPageModelCreator;
	
	@GetMapping("/main")
	public String mainPage(Model theModel,HttpServletRequest request) {
		
		ModelMap modelMap = mainPageModelCreator.createModel();
		
		theModel.addAllAttributes(modelMap);
		
		if((boolean)modelMap.get("isAuthenticated"))
			return "loggedReaderMain";
		else
			return "main";
		
	}

	@GetMapping("/join")
	public String joinPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "join";
	}

	@GetMapping("/login")
	public String loginPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "login";
	}

	@GetMapping("/showBook")
	public String showBook(@RequestParam(value = "bookId", required = false) int bookId, Model theModel,
			HttpSession session, Principal principal) throws Exception {

		Book book = bookService.getBook(bookId); // define proper name of method

		theModel.addAllAttributes(readerBookAndRatingModelCreator.createModel(bookId));
		theModel.addAttribute("quotesSplit", Arrays.asList(book.getBookQuotes().split("/")));
		theModel.addAttribute("bookPic", photoService.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("authorPic", photoService.getEncodedImage(book.getAuthorPhoto()));
		theModel.addAttribute("quantity", reviewService.ratingsAndReviewsQuantity(bookId));
		theModel.addAttribute("overallRating", ratingService.getOverallRating(bookId));
		theModel.addAttribute("bookReviews", reviewsReaderPhotoPreparer.getPreparedBookReviews(bookId));
		theModel.addAttribute("book", book);

		return "book";
	}

	@PostMapping("/saveReader")
	public String saveReader(@Valid @ModelAttribute("reader") Reader reader, BindingResult bindingResult,
			Model theModel, HttpSession session, WebRequest request) {

		if (bindingResult.hasErrors())
			return "join";

		if (!readerService.isThisLoginTaken(reader.getUsername())) {
			theModel.addAttribute("success", true);
			saveService.saveReader(reader);
		} else
			theModel.addAttribute("success", false);

		return "saved";
	}

	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("bookName") String bookName, @RequestParam("reviewId") int reviewId,
			HttpSession session, RedirectAttributes ra) {

		reviewService.deleteReviewById(reviewId);

		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/showCategory")
	public String showCategory(@RequestParam("categoryName") String category,
			@RequestParam(defaultValue = "1") int page, Model theModel) throws IOException {

		Page<Book> booksPage = bookService.getBooksByCategory(category, page);

		theModel.addAttribute("books", photoService.encodeBookPics(booksPage.getContent(),115,180));
		theModel.addAttribute("booksPage", booksPage);
		theModel.addAttribute("currentPage", page);
		theModel.addAttribute("category", category);

		return "categories";
	}

	@GetMapping("/searchBooks")
	public String searchBooks(Model theModel,@RequestParam(value = "phrase",required = false) String phrase) throws IOException {
		
		if(phrase != null)
			theModel.addAttribute("books",photoService.encodeBookPics(bookService.getBooksByPhrase(phrase),115,180));
		
		return "searchBooks";
	}
	
}
