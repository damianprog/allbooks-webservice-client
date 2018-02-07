package com.allbooks.webapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	ReaderService readerService;

	@Autowired
	ProfileService profileService;

	@GetMapping("/start")
	public String start(HttpSession session) {

		session.setAttribute("logged", false);

		return "redirect:/reader/main";
	}

	@GetMapping("/main")
	public String mainPage(Model theModel, HttpSession session) {

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

	@GetMapping("/loginPage")
	public String loginPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "login";
	}

	@GetMapping("/showBook")
	public String showBook(@RequestParam(value = "bookName", required = false) String bookName, Model theModel,
			HttpSession session) {

		if (bookName == null) {
			bookName = (String) session.getAttribute("redirectBookName");
		}

		if (session.getAttribute("logged").equals(true)) {

			ReaderBook readerBook;
			Reader reader = (Reader) session.getAttribute("loggedReader");

			readerBook = readerService.getReaderBook(bookName, reader.getId());

			if (readerBook != null) {
				theModel.addAttribute("readerBook", readerBook);
				theModel.addAttribute("update", true);
			} else {
				readerBook = new ReaderBook();
				theModel.addAttribute("readerBook", readerBook);
				theModel.addAttribute("update", false);
			}

			Rating readerRating = readerService.checkIfRated(reader.getId(), bookName);

			if (readerRating == null) {
				Rating rating = new Rating();
				theModel.addAttribute("rating", rating);
				theModel.addAttribute("userRated", false);
			} else if (readerRating != null) {
				theModel.addAttribute("readerRating", readerRating.getRate());
				theModel.addAttribute("userRated", true);
			}
		}

		int[] ratesAndReviews = readerService.howManyRatesAndReviews(bookName);
		theModel.addAttribute("rates", ratesAndReviews[0]);
		theModel.addAttribute("reviews", ratesAndReviews[1]);

		session.removeAttribute("params");

		double overallRating;

		if (bookName != null) {
			overallRating = readerService.getOverallRating(bookName);
		} else {
			overallRating = readerService.getOverallRating(bookName);
		}
		theModel.addAttribute("overallRating", overallRating);
		List<Review> bookReviews;

		bookReviews = readerService.getBookReviews(bookName, bookName);
		theModel.addAttribute("bookReviews", bookReviews);

		Review review = new Review();
		theModel.addAttribute("review", review);

		session.removeAttribute("redirectBookName");

		return "books/" + bookName;
	}

	@GetMapping("/reviewPage")
	public String reviewPage(@RequestParam Map<String, String> params, Model theModel, HttpSession session) {

		Map<String, String> sessionParams = (Map<String, String>) session.getAttribute("params");

		if (sessionParams != null)
			params = sessionParams;

		int reviewId = Integer.valueOf(params.get("reviewId"));
		int bookId = Integer.valueOf(params.get("bookId"));
		int readerRating = Integer.valueOf(params.get("readerRating"));
		String readerLogin = params.get("readerLogin");
		String bookName = readerService.getBookName(bookId);
		String fullBookName = params.get("fullBookName");
		String authorName = params.get("authorName");

		Review review = readerService.getOneReview(reviewId);
		Comment comment = new Comment();
		String reviewText = review.getText();

		List<Comment> reviewComments = readerService.getReviewComments(reviewId);

		theModel.addAttribute("bookId", bookId);
		theModel.addAttribute("readerRating", readerRating);
		theModel.addAttribute("readerLogin", readerLogin);
		theModel.addAttribute("bookName", bookName);
		theModel.addAttribute("fullBookName", fullBookName);
		theModel.addAttribute("reviewText", reviewText);
		theModel.addAttribute("authorName", authorName);
		theModel.addAttribute("reviewLikes", review.getLikes());
		theModel.addAttribute("reviewId", reviewId);
		theModel.addAttribute("comment", comment);
		theModel.addAttribute("reviewComments", reviewComments);

		return "review";
	}

	@PostMapping("/saveReader")
	public String saveReader(@ModelAttribute("reader") Reader reader, Model theModel, HttpSession session) {

		if (readerService.saveReader(reader)) {
			theModel.addAttribute("success", true);
		} else {
			theModel.addAttribute("success", false);
		}

		return "saved";
	}

	@PostMapping("/login")
	public String login(@RequestParam("login") String login, @RequestParam("password") String passowrd, Model theModel,
			HttpSession session) {

		Reader loggedReader = readerService.getReader(login, passowrd);

		if (loggedReader == null) {
			theModel.addAttribute("loggingError", true);
			return "saved";
		} else {
			session.setAttribute("loggedReader", loggedReader);
			session.setAttribute("logged", true);
			return "main";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model theModel) {

		session.removeAttribute("loggedReader");
		session.setAttribute("logged", false);

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "main";
	}

	@GetMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session) {

		Reader reader = (Reader) session.getAttribute("loggedReader");
		int readerId = reader.getId();
		rating.setBookId(readerService.getBookId(bookName));
		rating.setUserId(readerId);
		readerService.submitRating(rating);
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitReview")
	public String submitReview(@ModelAttribute("review") Review review, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session) {

		Reader reader = (Reader) session.getAttribute("loggedReader");
		int readerId = reader.getId();

		review.setReaderId(readerId);
		review.setReaderLogin(reader.getUsername());
		review.setReaderRating(readerService.getReaderRating(readerId, bookName));
		review.setBookId(readerService.getBookId(bookName));
		readerService.submitReview(review);
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitComment")
	public String submitComment(@ModelAttribute("comment") Comment comment, @RequestParam Map<String, String> params,
			@RequestParam("reviewId") int reviewId, HttpSession session, Model theModel) {

		Reader reader = (Reader) session.getAttribute("loggedReader");
		int readerId = reader.getId();

		comment.setReviewId(reviewId);
		comment.setReaderId(readerId);
		comment.setReaderLogin(reader.getUsername());
		comment.setReaderRating(readerService.getReaderRating(readerId, params.get("bookName")));
		readerService.submitComment(comment);

		session.setAttribute("params", params);

		return "redirect:/reader/reviewPage";
	}

	@GetMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookName") String bookName,
			HttpSession session, HttpServletRequest request) {

		readerService.dropLike(reviewId);
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/dropLikeReview")
	public String dropLikeReview(@RequestParam Map<String, String> params, Model theModel, HttpSession session) {

		int reviewId = Integer.valueOf(params.get("reviewId"));
		session.setAttribute("params", params);
		readerService.dropLike(reviewId);

		return "redirect:/reader/reviewPage";

	}

	@GetMapping("/readstate")
	public String readState(@RequestParam("bookName") String bookName, @RequestParam("update") boolean update,
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session) {

		Reader reader = (Reader) session.getAttribute("loggedReader");
		Book book = readerService.getBook(readerService.getBookId(bookName));
		double rating = readerService.getOverallRating(bookName);

		if (update == false) {

			int readerId = reader.getId();

			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String text = localDate.format(formatter);

			readerBook.setBookId(readerService.getBookId(bookName));
			readerBook.setReaderId(readerId);
			readerBook.setDateAdded(text);
			readerBook.setMinBookName(book.getTitle());
			readerBook.setFullBookName(book.getFullTitle());
			readerBook.setAuthor(book.getAuthor());
			readerBook.setRating(rating);

			readerService.saveReaderBook(readerBook);
		} else {
			readerService.saveNewState(readerBook.getShelves(), readerService.getBookId(bookName), reader.getId());
		}

		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam("myBooks") String myBooks,
			@RequestParam(value = "readerId", required = false) String readerId, Model theModel, HttpSession session) {

		int readerIdInt = 0;

		Reader reader;

		if (readerId != null) {
			readerIdInt = Integer.valueOf(readerId);
			reader = profileService.getReaderById(readerIdInt);
		} else
			reader = (Reader) session.getAttribute("loggedReader");

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());

		for (ReaderBook tempReaderBook : readerBooks) {

			int readerRating = readerService.getReaderRating(reader.getId(), tempReaderBook.getMinBookName());
			tempReaderBook.setReaderRating(readerRating);
			tempReaderBook.setRating(readerService.getOverallRating(tempReaderBook.getMinBookName()));
		}

		boolean myBooksBoo = Boolean.valueOf(myBooks);
		System.out.println(readerBooks);
		theModel.addAttribute("readerBooks", readerBooks);
		theModel.addAttribute("myBooks", myBooksBoo);
		theModel.addAttribute("reader", reader.getUsername());

		return "mybooks";
	}

}
