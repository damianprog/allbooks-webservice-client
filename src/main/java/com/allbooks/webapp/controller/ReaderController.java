package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.allbooks.webapp.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.ControllerUtils;
import com.allbooks.webapp.utils.ResizePhoto;
import com.allbooks.webapp.utils.save.SaveService;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	ApplicationEventPublisher eventPublisher;

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
			HttpSession session, Principal principal) {

		Book book = bookService.getBookByName(bookName);

		if (principal != null) {
			Reader reader = readerService.getReaderByUsername(principal.getName());

			ReaderBook readerBook = bookService.getReaderBook(bookName, reader.getId());

			if (readerBook == null)
				readerBook = new ReaderBook();

			theModel.addAttribute("readerBook", readerBook);

			Rating rating = bookService.getReaderRatingObject(reader.getId(), bookName);
			
			if (rating == null)
				rating = new Rating();

			theModel.addAttribute("rating", rating);
		}

		String quotes = book.getBookQuotes();

		List<String> quotesSplit = Arrays.asList(quotes.split("/"));
		theModel.addAttribute("quotesSplit", quotesSplit);

		String bookPic = ControllerUtils.getEncodedImage(book.getBookPhoto());
		String authorPic = ControllerUtils.getEncodedImage(book.getAuthorPhoto());

		theModel.addAttribute("bookPic", bookPic);
		theModel.addAttribute("authorPic", authorPic);

		int[] ratesAndReviews = bookService.howManyRatesAndReviews(bookName);
		theModel.addAttribute("rates", ratesAndReviews[0]);
		theModel.addAttribute("reviews", ratesAndReviews[1]);

		double overallRating = bookService.getOverallRating(bookName);
		theModel.addAttribute("overallRating", overallRating);

		List<Review> bookReviews = bookService.getBookReviews(bookName);

		if (bookReviews != null) {
//			for (Review tempReview : bookReviews) {
//				int rating = bookService.getReaderRating(tempReview.getReaderIdentity(), book.getMiniTitle());
//				tempReview.setReaderRating(rating);
//			}

			bookReviews.sort(Comparator.comparingInt(Review::getId).reversed());
		}

		theModel.addAttribute("bookReviews", bookReviews);

		Review review = new Review();
		theModel.addAttribute("review", review);

		theModel.addAttribute("book", book);

		return "book";
	}

	@GetMapping("/registrationConfirm")
	public String registrationConfirm(@RequestParam("token") String token, @RequestParam("readerId") Integer readerId,
			Model theModel, HttpSession session, Principal principal) {

		Reader reader = readerService.getReaderById(readerId);

		if (reader == null) {
			theModel.addAttribute("success", false);
			return "registrationConfirm";
		}

		if (reader.isEnabled()) {
			theModel.addAttribute("alreadyDone", true);
			return "registrationConfirm";
		}

		VerificationToken tokenObj = tokenService.getTokenByReaderId(readerId);

		if (token.equals(tokenObj.getToken())) {
			reader.setEnabled(true);
			readerService.updateReader(reader);
			theModel.addAttribute("success", true);
			tokenService.deleteVerificationTokenTokenById(tokenObj.getId());
		} else
			theModel.addAttribute("success", false);

		return "registrationConfirm";
	}

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam(value = "readerId", required = false) Integer readerId, Model theModel,
			HttpSession session, Principal principal) {

		boolean myBooksBoo = false;
		Reader reader;

		if (readerId != null) {
			reader = readerService.getReaderById(readerId);
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		if ((principal != null) && (principal.getName().equals(reader.getUsername())))
			myBooksBoo = true;

		List<ReaderBook> readerBooks = bookService.getReaderBooks(reader.getId());
		;

		for (ReaderBook tempReaderBook : readerBooks) {
			int readerRating = bookService.getReaderRating(reader.getId(), tempReaderBook.getMinBookName());
			tempReaderBook.setReaderRating(readerRating);
			tempReaderBook.setRating(bookService.getOverallRating(tempReaderBook.getMinBookName()));
			tempReaderBook.setEncodedBookPic(ControllerUtils.getEncodedImage(tempReaderBook.getBookPic()));
		}

		theModel.addAttribute("readerBooks", readerBooks);
		theModel.addAttribute("myBooks", myBooksBoo);
		theModel.addAttribute("reader", reader.getUsername());

		return "mybooks";
	}

	@GetMapping("/postReviewPage")
	public String postReviewPage(@RequestParam("bookId") int bookId, Model theModel, Principal principal)
			throws IOException {

		Book book = bookService.getBook(bookId);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		byte[] bookPicBytes = ResizePhoto.resize(book.getBookPhoto(), 80, 120);

		Rating rating = bookService.getReaderRatingObject(reader.getId(), book.getMiniTitle());

		if (rating == null)
			rating = new Rating();

		ReaderBook readerBook = bookService.getReaderBook(book.getMiniTitle(), reader.getId());

		if (readerBook == null)
			readerBook = new ReaderBook();

		theModel.addAttribute("readerBook", readerBook);
		theModel.addAttribute("book", book);
		theModel.addAttribute("bookPic", ControllerUtils.getEncodedImage(bookPicBytes));
		theModel.addAttribute("rating", rating);

		return "postreview";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/reviewPage")
	public String reviewPage(@RequestParam(required = false) Map<String, String> params, Model theModel,
			HttpSession session) {

		if (params.isEmpty()) {
			params = (Map<String, String>) session.getAttribute("redirectParams");
			session.removeAttribute("redirectParams");
		}

		int reviewId = Integer.valueOf(params.get("reviewId"));
		Review review = bookService.getOneReview(reviewId);

		Book book = bookService.getBook(review.getBookId());

		Comment comment = new Comment();

		List<Comment> reviewComments = bookService.getReviewComments(reviewId);

//		for (Comment comm : reviewComments)
//			comm.setReaderRating(bookService.getReaderRating(comm.getReaderId(), book.getMiniTitle()));

		reviewComments.sort(Comparator.comparingInt(Comment::getId).reversed());

		theModel.addAttribute("book", book);
		theModel.addAttribute("review", review);
		theModel.addAttribute("bookPic", ControllerUtils.getEncodedImage(book.getBookPhoto()));
		theModel.addAttribute("rating", review.getRating());
		theModel.addAttribute("readerLogin", review.getReaderLogin());
		theModel.addAttribute("comment", comment);
		theModel.addAttribute("reviewComments", reviewComments);

		return "review";
	}

	@PostMapping("/saveReader")
	public String saveReader(@Valid @ModelAttribute("reader") Reader reader, BindingResult bindingResult,
			Model theModel, HttpSession session, WebRequest request) {

		if (bindingResult.hasErrors()) {
			return "join";
		}
		if (readerService.saveReader(reader)) {
//			theModel.addAttribute("success", true);
			Reader readerPass = readerService.getReaderByUsername(reader.getUsername());
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(readerPass, request.getLocale()));
		} else {
			theModel.addAttribute("success", false);
		}

		return "saved";
	}

	@GetMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating,BindingResult resultRating ,@RequestParam("bookName") String bookName,
			@RequestParam("update") Boolean update,Model theModel, HttpSession session, Principal principal,
			RedirectAttributes ra) {
		
		System.out.println(rating.toString());
		
		saveService.saveRating(rating, update, bookName, principal.getName());

		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitReview") // POST
	public String submitReview(@ModelAttribute("review") Review review, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		saveService.saveReview(review, principal.getName(), bookName);

		ra.addAttribute("bookName", bookName);

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

		Reader reader = readerService.getReaderByUsername(principal.getName());
		int readerId = reader.getId();

		comment.setReaderId(readerId);
		comment.setReaderLogin(reader.getUsername());
		comment.setRating(bookService.getReaderRatingObject(readerId, params.get("bookName")));

		Review review = bookService.getOneReview(reviewId);
		List<Comment> comments = review.getComments();

		if (comments == null)
			comments = new ArrayList<Comment>();

		comments.add(comment);
		review.setComments(comments);

		bookService.updateReview(review);

		session.setAttribute("redirectParams", params);

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
	public String dropLikeReview(@RequestParam Map<String, String> params, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		int reviewId = Integer.valueOf(params.get("reviewId"));
		bookService.dropLike(reviewId);
		session.setAttribute("redirectParams", params);

		return "redirect:/reader/reviewPage";

	}

	@GetMapping("/readState")
	public String readState(@RequestParam("bookName") String bookName, @RequestParam("update") boolean update,
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session, Principal principal,
			RedirectAttributes ra) throws IOException {

		saveService.saveReaderBook(bookName, update, readerBook, principal.getName());

		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/deleteReaderBook")
	public String deleteReaderBook(@RequestParam("readerBookId") int id, Model theModel) {

		bookService.deleteReaderBookById(id);

		return "redirect:/reader/showMyBooks";
	}

	@GetMapping("/accessDenied")
	public String accessDenied(Model theModel) {
		//errors handling
		theModel.addAttribute("accessDenied", true);

		return "saved";
	}

	@PostMapping("/postReview")
	public String postReview(@RequestParam Map<String, String> params, @ModelAttribute("rating") Rating rating,
			@ModelAttribute("readerBook") ReaderBook readerBook, RedirectAttributes ra, Principal principal) throws IOException {

		Book book = bookService.getBookByName(params.get("bookName"));
		String username = principal.getName();
		
		Review review = new Review();
		review.setTitle(params.get("title"));
		review.setText(params.get("text"));
		
		saveService.saveRating(rating, Boolean.valueOf(params.get("updateRating")), book.getMiniTitle(), username);
		saveService.saveReaderBook(book.getMiniTitle(), Boolean.valueOf(params.get("updateReaderBook")), readerBook, username);
		saveService.saveReview(review, username, book.getMiniTitle());
		
		ra.addAttribute("bookName",book.getMiniTitle());
		
		return "redirect:/reader/showBook";
	}

}
