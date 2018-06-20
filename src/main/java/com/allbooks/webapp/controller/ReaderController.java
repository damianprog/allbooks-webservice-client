package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.ReaderBookAndRatingModelCreator;
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

	@GetMapping("/main")
	public String mainPage(Model theModel, HttpSession session, HttpServletRequest request) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

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
		theModel.addAttribute("bookReviews", reviewService.getBookReviews(bookId));
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

	// temporary method
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

		theModel.addAttribute("books", photoService.encodeBookPics(booksPage.getContent()));
		theModel.addAttribute("booksPage", booksPage);
		theModel.addAttribute("currentPage", page);
		theModel.addAttribute("category", category);

		return "categories";
	}

}
