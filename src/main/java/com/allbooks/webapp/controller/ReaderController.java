package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.ControllerUtils;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	ReaderService readerService;

	@Autowired
	ProfileService profileService;

	@GetMapping("/start")
	public String start(HttpSession session) {

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

	@GetMapping("/login")
	public String loginPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "login";
	}

	@GetMapping("/showBook")
	public String showBook(@RequestParam(value = "bookName", required = false) String bookName, Model theModel,
			HttpSession session, Principal principal) {

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

		String base64Encoded = ProfileController.getEncodedImage(book.getBookPhoto());
		String base64Encoded2 = ProfileController.getEncodedImage(book.getAuthorPhoto());

		theModel.addAttribute("bookPic", base64Encoded);
		theModel.addAttribute("authorPic", base64Encoded2);

		int[] ratesAndReviews = readerService.howManyRatesAndReviews(bookName);
		theModel.addAttribute("rates", ratesAndReviews[0]);
		theModel.addAttribute("reviews", ratesAndReviews[1]);

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
				int rating = readerService.getReaderRating(tempReview.getReaderIdentity(), book.getMiniTitle());
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

	@GetMapping("/registrationConfirm")
	public String registrationConfirm(@RequestParam("token") String token, @RequestParam("readerId") Integer readerId,
			Model theModel, HttpSession session, Principal principal) {
		
		Reader reader = profileService.getReaderById(readerId);
		
		if(reader == null) {
			theModel.addAttribute("success",false);
			return "registrationConfirm";
		}
		
		if(reader.isEnabled()) {
			theModel.addAttribute("alreadyDone",true);
			return "registrationConfirm";
		}
		
		VerificationToken tokenObj = readerService.getTokenByReaderId(readerId);
		
		if(token.equals(tokenObj.getToken())) {
			reader.setEnabled(true);
			readerService.updateReader(reader);
			theModel.addAttribute("success",true);
			readerService.deleteTokenById(tokenObj.getId());
		}else
			theModel.addAttribute("success",false);
			
		
		return "registrationConfirm";
	}

	@GetMapping("/showMyBooks")
	public String showMyBooks(@RequestParam(value = "readerId", required = false) String readerId, Model theModel, HttpSession session,
			Principal principal) {

		boolean myBooksBoo=false;
		int readerIdInt = 0;
		Reader reader;


		if (readerId != null) {
			readerIdInt = Integer.valueOf(readerId);
			reader = profileService.getReaderById(readerIdInt);
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		if((principal != null) && (principal.getName().equals(reader.getUsername())))
			myBooksBoo = true;
		
		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());

		Map<String, String> bookPics = new HashMap<>();

		for (ReaderBook tempReaderBook : readerBooks) {
			int readerRating = readerService.getReaderRating(reader.getId(), tempReaderBook.getMinBookName());
			tempReaderBook.setReaderRating(readerRating);
			tempReaderBook.setRating(readerService.getOverallRating(tempReaderBook.getMinBookName()));
			tempReaderBook.setEncodedBookPic(ProfileController.getEncodedImage(tempReaderBook.getBookPic()));
		}

		theModel.addAttribute("bookPicsMap", bookPics);
		theModel.addAttribute("readerBooks", readerBooks);
		theModel.addAttribute("myBooks", myBooksBoo);
		theModel.addAttribute("reader", reader.getUsername());

		return "mybooks";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/reviewPage")
	public String reviewPage(@RequestParam(required=false) Map<String, String> params, Model theModel,
			HttpSession session) {

		if (params.isEmpty()) {
			params = (Map<String, String>) session.getAttribute("redirectParams");
			session.removeAttribute("redirectParams");
		}
		
		int bookId = Integer.valueOf(params.get("bookId"));
		Book book = readerService.getBook(bookId);	
		
		int reviewId = Integer.valueOf(params.get("reviewId"));	
		int readerRating = Integer.valueOf(params.get("readerRating"));
		String readerLogin = params.get("readerLogin");
		String bookName = book.getMiniTitle();
		String fullBookName = book.getFullTitle();
		String authorName = book.getAuthor();

		Review review = readerService.getOneReview(reviewId);
		Comment comment = new Comment();
		String reviewText = review.getText();

		List<Comment> reviewComments = readerService.getReviewComments(reviewId);

		reviewComments.sort(Comparator.comparingInt(Comment::getId).reversed());

		String bookPicEncoded = ProfileController.getEncodedImage(book.getBookPhoto());

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
	public String saveReader(@ModelAttribute("reader") Reader reader, Model theModel, HttpSession session,
			WebRequest request) {

		if (readerService.saveReader(reader)) {
			theModel.addAttribute("success", true);
			Reader readerPass = readerService.getReaderByUsername(reader.getUsername());
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(readerPass, request.getLocale()));
		} else {
			theModel.addAttribute("success", false);
		}

		return "saved";
	}

	@GetMapping("/rate")
	public String rate(@ModelAttribute("rating") Rating rating, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		rating.setBookId(readerService.getBookId(bookName));
		rating.setReaderIdentity(reader.getId());

		List<Rating> ratings = reader.getRatings();

		if (ratings == null)
			ratings = new ArrayList<>();

		ratings.add(rating);
		reader.setRatings(ratings);

		readerService.submitRating(rating);
		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/submitReview") // POST
	public String submitReview(@ModelAttribute("review") Review review, @RequestParam("bookName") String bookName,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		int readerId = reader.getId();

		review.setReaderIdentity(readerId);
		review.setReaderLogin(reader.getUsername());
		review.setReaderRating(readerService.getReaderRating(readerId, bookName));
		review.setBookId(readerService.getBookId(bookName));
		review.setBookTitle(bookName);

		List<Review> reviewsList = reader.getReviews();

		if (reviewsList == null)
			reviewsList = new ArrayList<Review>();

		reviewsList.add(review);

		reader.setReviews(reviewsList);

		readerService.submitReview(review, readerId);
		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	// temporary method
	@GetMapping("/deleteReview")
	public String deleteReview(@RequestParam("bookName") String bookName, @RequestParam("reviewId") int reviewId,
			HttpSession session, RedirectAttributes ra) {

		readerService.deleteReviewById(reviewId);

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
		comment.setReaderRating(readerService.getReaderRating(readerId, params.get("bookName")));

		Review review = readerService.getOneReview(reviewId);
		List<Comment> comments = review.getComments();

		if (comments == null)
			comments = new ArrayList<Comment>();

		comments.add(comment);
		review.setComments(comments);

		readerService.updateReview(review);

		session.setAttribute("redirectParams",params);
		
		return "redirect:/reader/reviewPage";
	}

	@GetMapping("/dropLike")
	public String dropLike(@RequestParam("reviewId") int reviewId, @RequestParam("bookName") String bookName,
			HttpSession session, HttpServletRequest request, RedirectAttributes ra) {

		readerService.dropLike(reviewId);
		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/dropLikeReview")
	public String dropLikeReview(@RequestParam Map<String, String> params, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		int reviewId = Integer.valueOf(params.get("reviewId"));
		readerService.dropLike(reviewId);
		session.setAttribute("redirectParams",params);

		return "redirect:/reader/reviewPage";

	}

	@GetMapping("/readState")
	public String readState(@RequestParam("bookName") String bookName, @RequestParam("update") boolean update,
			@ModelAttribute("readerBook") ReaderBook readerBook, HttpSession session, Principal principal,
			RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		Book book = readerService.getBook(readerService.getBookId(bookName));

		byte[] bookPicBytes = book.getBookPhoto();
		InputStream in = new ByteArrayInputStream(bookPicBytes);
		BufferedImage imgToResize = ImageIO.read(in);
		BufferedImage resizedBookPic = ControllerUtils.resize(imgToResize, 125, 190);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedBookPic, "jpg", baos);
		byte[] bookPic = baos.toByteArray();

		readerBook.setBookPic(bookPic);

		double rating = readerService.getOverallRating(bookName);

		int readerId = reader.getId();

		if (update == false) {

			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String text = localDate.format(formatter);

			readerBook.setBookId(readerService.getBookId(bookName));
			readerBook.setReaderIdentity(readerId);
			readerBook.setDateAdded(text);
			readerBook.setMinBookName(book.getMiniTitle());
			readerBook.setFullBookName(book.getFullTitle());
			readerBook.setAuthor(book.getAuthor());
			readerBook.setRating(rating);

			List<ReaderBook> readerBooks = reader.getReaderBooks();

			if (readerBooks == null)
				readerBooks = new ArrayList<>();

			readerBooks.add(readerBook);
			reader.setReaderBooks(readerBooks);

			readerService.saveReaderBook(readerBook);
		} else {
			readerService.saveNewState(readerBook.getShelves(), readerService.getBookId(bookName), readerId);
		}

		ra.addAttribute("bookName", bookName);

		return "redirect:/reader/showBook";
	}

	@GetMapping("/deleteReaderBook")
	public String deleteReaderBook(@RequestParam("readerBookId") int id,Model theModel) {
		
		readerService.deleteReaderBookById(id);
		
		return "redirect:/reader/showMyBooks";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied(Model theModel) {
		
		theModel.addAttribute("accessDenied",true);
		
		return "saved";
	} 
	
}
