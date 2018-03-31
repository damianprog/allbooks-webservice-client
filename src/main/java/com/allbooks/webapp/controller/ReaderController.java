package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.ReaderBooksHandler;
import com.allbooks.webapp.utils.RegistrationConfirmation;
import com.allbooks.webapp.utils.SubmitComment;
import com.allbooks.webapp.utils.save.SaveService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	ReaderService readerService;

	@Autowired
	BookService bookService;

	@Autowired
	ProfileService profileService;

	@Autowired
	TokenService tokenService;

	@Autowired
	SaveService saveService;

	@Autowired
	SubmitComment submitComment;

	@Autowired
	RegistrationConfirmation registrationConfirmation;

	@Autowired
	ReaderBooksHandler readerBooksHandler;

	@Autowired
	PhotoServiceImpl photoService;

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
	public String showBook(@RequestParam(value = "bookName", required = false) String bookName, Model theModel,
			HttpSession session, Principal principal) throws Exception {

		Book book = bookService.getBookByName(bookName);

		if (principal != null) {
			theModel.addAttribute("readerBook", bookService.getReaderBook(book.getId(), principal.getName()));
			theModel.addAttribute("rating", bookService.getReaderRatingObject(bookName, principal.getName()));
		}

		theModel.addAttribute("quotesSplit", Arrays.asList(book.getBookQuotes().split("/")));
		theModel.addAttribute("bookPic", photoService.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("authorPic", photoService.getEncodedImage(book.getAuthorPhoto()));
		theModel.addAttribute("quantity", bookService.ratingsAndReviewsQuantity(bookName));
		theModel.addAttribute("overallRating", bookService.getOverallRating(bookName));
		theModel.addAttribute("bookReviews", bookService.getBookReviews(bookName));
		theModel.addAttribute("review", new Review());
		theModel.addAttribute("book", book);

		return "book";
	}

	@GetMapping("/registrationConfirm")
	public String registrationConfirm(@RequestParam("token") String token, @RequestParam("readerId") Integer readerId,
			Model theModel, HttpSession session, Principal principal) {

		Map<String, Boolean> map = registrationConfirmation.verifyConfirmation(readerId, token);

		theModel.addAttribute("success", map.get("success"));
		theModel.addAttribute("alreadyDone", map.get("alreadyDone"));

		return "registrationConfirm";
	}

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam(value = "readerId", required = false) Integer readerId, Model theModel,
			HttpSession session, Principal principal) {

		theModel.addAttribute("readerBooks", readerBooksHandler.prepareReaderBooksForMyBooks(readerId));
		theModel.addAttribute("readerLogin", readerService.getReaderById(readerId).getUsername());

		return "mybooks";
	}

	@GetMapping("/reviewPage")
	public String reviewPage(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session) {

		Review review = bookService.getReviewById(reviewId);

		Book book = bookService.getBook(review.getBookId());

		List<Comment> reviewComments = bookService.getReviewComments(reviewId);

		theModel.addAttribute("book", bookService.getBook(review.getBookId()));
		theModel.addAttribute("review", bookService.getReviewById(reviewId));
		theModel.addAttribute("bookPic", photoService.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("rating", review.getRating());
		theModel.addAttribute("readerLogin", review.getReaderLogin());
		theModel.addAttribute("comment", new Comment());
		theModel.addAttribute("reviewComments", reviewComments);

		return "review";
	}

	@PostMapping("/saveReader")
	public String saveReader(@Valid @ModelAttribute("reader") Reader reader, BindingResult bindingResult,
			Model theModel, HttpSession session, WebRequest request) {

		if (bindingResult.hasErrors())
			return "join";

		theModel.addAttribute("success", saveService.saveReader(reader));

		return "saved";
	}

	@GetMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, BindingResult resultRating,
			@RequestParam Map<String, String> params, Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		saveService.saveRating(rating, params, principal.getName());

		ra.addAttribute("bookName", params.get("bookName"));

		return "redirect:/reader/showBook";
	}

	// temporary method
	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("bookName") String bookName, @RequestParam("reviewId") int reviewId,
			HttpSession session, RedirectAttributes ra) {

		bookService.deleteReviewById(reviewId);

		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitComment")
	public String submitComment(@ModelAttribute("comment") Comment comment, @RequestParam Map<String, String> params,
			@RequestParam("reviewId") int reviewId, HttpSession session, Model theModel, Principal principal,
			RedirectAttributes ra) {

		submitComment.submit(comment, params);

		ra.addAttribute("reviewId", Integer.valueOf(params.get("reviewId")));

		return "redirect:/reader/reviewPage";
	}

	@GetMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookName") String bookName,
			HttpSession session, HttpServletRequest request, RedirectAttributes ra) {

		bookService.dropLike(reviewId);
		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/dropLikeReview")
	public String dropLikeReview(@RequestParam("reviewId") int reviewId, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		bookService.dropLike(reviewId);
		ra.addAttribute("reviewId", reviewId);

		return "redirect:/reader/reviewPage";

	}

	@GetMapping("/readState")
	public String readState(@RequestParam Map<String, String> params,
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session, Principal principal,
			RedirectAttributes ra) throws IOException {

		saveService.saveReaderBook(readerBook,params ,principal.getName());

		ra.addAttribute("bookName", params.get("bookName"));

		return "redirect:/reader/showBook";
	}

	@GetMapping("/deleteReaderBook")
	public String deleteReaderBook(@RequestParam("bookId") int bookId, Model theModel, RedirectAttributes ra,
			Principal principal) {

		bookService.deleteReaderBookById(bookId, principal.getName());

		ra.addAttribute("readerId", readerService.getReaderByUsername(principal.getName()).getId());

		return "redirect:/reader/showMyBooks";
	}

	@PostMapping("/postReview")
	public String postReview(@RequestParam Map<String, String> params, @ModelAttribute("rating") Rating rating,
			@ModelAttribute("readerBook") ReaderBook readerBook, RedirectAttributes ra, Principal principal)
			throws IOException {

		String username = principal.getName();
		readerBook.setId(Integer.valueOf(params.get("readerBookId")));

		saveService.saveRating(rating, params, username);
		saveService.saveReaderBook(readerBook, params, username);
		saveService.saveReview(params, username);

		ra.addAttribute("bookName", params.get("bookName"));

		return "redirect:/reader/showBook";
	}

	@GetMapping("/postReviewPage")
	public String postReviewPage(@RequestParam("bookId") int bookId, Model theModel, Principal principal)
			throws IOException {

		Book book = bookService.getBook(bookId);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		byte[] bookPicBytes = photoService.resize(book.getBookPhoto(), 80, 120);

		theModel.addAttribute("readerBook", bookService.getReaderBook(book.getId(), reader.getUsername()));
		theModel.addAttribute("book", book);
		theModel.addAttribute("bookPic", photoService.getEncodedImage(bookPicBytes));
		theModel.addAttribute("rating", bookService.getReaderRatingObject(book.getMiniTitle(), reader.getUsername()));

		return "postreview";
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
