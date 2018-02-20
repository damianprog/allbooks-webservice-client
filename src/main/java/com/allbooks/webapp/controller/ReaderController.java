package com.allbooks.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.ProfilePics;
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

	@GetMapping("/loginPage")
	public String loginPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "login";
	}

	@GetMapping("/showBook")
	public String showBook(@RequestParam(value = "bookName", required = false) String bookName, Model theModel,
			HttpSession session, Principal principal) {

		if (bookName == null) {
			bookName = (String) session.getAttribute("redirectBookName");
		}
		Book book = readerService.getBookByName(bookName);

		if (principal != null) {
			ReaderBook readerBook;
			Reader reader = readerService.getReaderByUsername(principal.getName());

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

		String quotes = book.getBookQuotes();

		List<String> quotesSplit = Arrays.asList(quotes.split("/"));
		theModel.addAttribute("quotesSplit", quotesSplit);

		String base64Encoded = getEncodedImage(book.getBookPhoto());
		String base64Encoded2 = getEncodedImage(book.getAuthorPhoto());

		theModel.addAttribute("bookPic", base64Encoded);
		theModel.addAttribute("authorPic", base64Encoded2);

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

		// update current reader ratings in reviews
		if (bookReviews != null) {
			for (Review tempReview : bookReviews) {
				int rating = readerService.getReaderRating(tempReview.getReaderIdentity(), book.getTitle());
				tempReview.setReaderRating(rating);
			}

			bookReviews.sort(Comparator.comparingInt(Review::getId).reversed());
		}

		theModel.addAttribute("bookReviews", bookReviews);

		Review review = new Review();
		theModel.addAttribute("review", review);

		theModel.addAttribute("book", book);

		return "book";
	}

	public String getEncodedImage(byte[] theEncodedBase64) {

		String base64Encoded = null;

		byte[] encodeBase64 = Base64.getEncoder().encode(theEncodedBase64);
		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return base64Encoded;
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

		reviewComments.sort(Comparator.comparingInt(Comment::getId).reversed());

		Book book = readerService.getBook(bookId);
		
		String bookPicEncoded = getEncodedImage(book.getBookPhoto());
		
		theModel.addAttribute("bookId", bookId);
		theModel.addAttribute("bookPic", bookPicEncoded);
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
			Model theModel, HttpSession session, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		rating.setBookId(readerService.getBookId(bookName));
		rating.setReaderIdentity(reader.getId());
		
		List<Rating> ratings =  reader.getRatings();
		
		if(ratings == null)
			ratings = new ArrayList<>();
		
		ratings.add(rating);
		reader.setRatings(ratings);
		
		readerService.submitRating(rating);
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitReview") // POST
	public String submitReview(@ModelAttribute("review") Review review, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		int readerId = reader.getId();

		review.setReaderIdentity(readerId);
		review.setReaderLogin(reader.getUsername());
		review.setReaderRating(readerService.getReaderRating(readerId, bookName));
		review.setBookId(readerService.getBookId(bookName));
		
		List<Review> reviewsList = reader.getReviews();

		if(reviewsList == null)
			reviewsList = new ArrayList<Review>();
		
		reviewsList.add(review);

		reader.setReviews(reviewsList);

		readerService.submitReview(review,readerId);
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}
	
	//temporary method
	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("bookName") String bookName,@RequestParam("reviewId") int reviewId,HttpSession session) {
		
		readerService.deleteReviewById(reviewId);
		
		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitComment")
	public String submitComment(@ModelAttribute("comment") Comment comment, @RequestParam Map<String, String> params,
			@RequestParam("reviewId") int reviewId, HttpSession session, Model theModel, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		int readerId = reader.getId();

		comment.setReaderId(readerId);
		comment.setReaderLogin(reader.getUsername());
		comment.setReaderRating(readerService.getReaderRating(readerId, params.get("bookName")));

		Review review = readerService.getOneReview(reviewId);
		List<Comment> comments = review.getComments();

		if (comments == null)
			comments = new ArrayList<Comment>();

		comments.add(comment);
		review.setComments(comments);

		readerService.updateReview(review);

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
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		Book book = readerService.getBook(readerService.getBookId(bookName));
		double rating = readerService.getOverallRating(bookName);
		
		int readerId = reader.getId();

		if (update == false) {


			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String text = localDate.format(formatter);

			readerBook.setBookId(readerService.getBookId(bookName));
			readerBook.setReaderIdentity(readerId);
			readerBook.setDateAdded(text);
			readerBook.setMinBookName(book.getTitle());
			readerBook.setFullBookName(book.getFullTitle());
			readerBook.setAuthor(book.getAuthor());
			readerBook.setRating(rating);

			List<ReaderBook> readerBooks = reader.getReaderBooks();
			
			if(readerBooks == null)
				readerBooks = new ArrayList<>();
			
			readerBooks.add(readerBook);
			reader.setReaderBooks(readerBooks);
			
			readerService.saveReaderBook(readerBook);
		} else {
			readerService.saveNewState(readerBook.getShelves(), readerService.getBookId(bookName), readerId);
		}

		session.setAttribute("redirectBookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam("myBooks") String myBooks,
			@RequestParam(value = "readerId", required = false) String readerId, Model theModel, HttpSession session,
			Principal principal) {

		int readerIdInt = 0;

		Reader reader;

		if (readerId != null) {
			readerIdInt = Integer.valueOf(readerId);
			reader = profileService.getReaderById(readerIdInt);
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());

		for (ReaderBook tempReaderBook : readerBooks) {

			System.out.println(tempReaderBook.getMinBookName());
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
