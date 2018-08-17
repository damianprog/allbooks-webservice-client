package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

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

import com.allbooks.webapp.entity.FavoriteGenres;
import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.model.FriendsRequestsOptionsModelCreator;
import com.allbooks.webapp.utils.photos.ReaderPostsWithPreparedReadersPhotoGetter;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/loggedReader")
public class LoggedReaderController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired	
	private ReaderService readerService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter readerPostsGetter;

	@Autowired
	private FavoriteGenresService favoriteGenresService;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private FriendsRequestsOptionsModelCreator friendsRequestsOptionsModelCreator;

	@GetMapping("/showNotifications")
	public String showNotificationsPage(Model theModel, Principal principal, HttpSession session,
			@RequestParam(defaultValue = "1") int page) {

		int readerId = contextService.getLoggedReaderId();

		Page<Notification> notifications = notificationService.getNotificationsByReaderId(readerId, page);

		theModel.addAttribute("notifications", notifications.getContent());
		theModel.addAttribute("notificationsPage", notifications);
		theModel.addAttribute("currentPage", page);

		return "notifications";

	}

	@GetMapping("/deleteNotification")
	public String deleteNotification(Model theModel, @RequestParam("notificationId") int notificationId,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		notificationService.deleteNotificationByIdAndReaderId(notificationId, readerId);

		return "redirect:/loggedReader/showNotifications";
	}

	@GetMapping("/readingChallange")
	public String readingChallange(Model theModel, @RequestParam("numberOfBooks") int numberOfBooks,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = new ReadingChallange(reader, numberOfBooks);

		readingChallangeService.saveReadingChallange(readingChallange);

		return "redirect:/reader/main";
	}

	@GetMapping("/showReadingChallange")
	public String showReadingChallange(Model theModel, @RequestParam("readerId") int readerId) {

		Reader reader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (reader.getProfilePhoto() != null) {
			String resizedProfilePhoto = photoService
					.getEncodedImage(photoService.resize(reader.getProfilePhoto(), 80, 80));
			reader.setEncodedProfilePhoto(resizedProfilePhoto);
		}

		List<ReaderBook> currentYearBooks = currentYearReadBooksGetter.getBooks(readerId);

		List<ReaderPost> readingChallangeComments = readerPostsGetter.getPreparedReadingChallangeComments(readerId);

		photoService.encodeAndResizeBookPhotoInBookChildren(currentYearBooks, 100, 150);

		theModel.addAttribute("reader", reader);
		theModel.addAttribute("readBooks", currentYearBooks);
		theModel.addAttribute("readingChallange", readingChallange);
		theModel.addAttribute("readingChallangeComments", readingChallangeComments);

		return "readingChallange";
	}

	@GetMapping("/showFavoriteGenres")
	public String showFavoriteGenres(Model theModel,
			@RequestParam(value = "savedAlert", defaultValue = "false") boolean savedAlert) {

		int readerId = contextService.getLoggedReaderId();

		FavoriteGenres favoriteGenres = favoriteGenresService.getFavoriteGenresByReaderId(readerId);

		if (favoriteGenres != null) {

			List<String> genres = Arrays.asList(favoriteGenres.getFavoriteGenres().split(","));

			String jsonArray = new Gson().toJson(genres);

			theModel.addAttribute("favoriteGenres", jsonArray);
		}

		theModel.addAttribute("savedAlert", savedAlert);

		return "favoriteGenres";
	}

	@PostMapping("/saveFavoriteGenres")
	public String saveFavoriteGenres(Model theModel, RedirectAttributes ra,
			@RequestParam("categoryChecked") List<String> categoriesChecked) {

		FavoriteGenres fGenres;

		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		fGenres = favoriteGenresService.getFavoriteGenresByReaderId(readerId);

		if (fGenres == null) {
			fGenres = new FavoriteGenres();
			fGenres.setReader(reader);
		}

		fGenres.setFavoriteGenres(String.join(",", categoriesChecked));

		favoriteGenresService.saveFavoriteGenres(fGenres);

		ra.addAttribute("savedAlert", true);

		return "redirect:/loggedReader/showFavoriteGenres";

	}

	@GetMapping("/showFriends")
	public String showFriends(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Reader> friends = friendsService.getReaderFriends(readerId);

		photoService.setResizedAndEncodedPhotosInReaders(friends, 80, 80);

		theModel.addAttribute("friends", friends);

		return "friends";
	}

	@GetMapping("/showFriendsRequests")
	public String showFriendsRequests(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Pending> friendsRequests = pendingService.getFriendsInvitesByReaderId(readerId);

		theModel.addAttribute("friendsRequests", friendsRequests);

		return "friendsRequests";
	}

	@GetMapping("/showAddFriends")
	public String showAddFriends(Model theModel, @RequestParam(value = "username", required = false) String username) {

		int readerId = contextService.getLoggedReaderId();

		Reader searchedReader = null;

		theModel.addAttribute("isAlreadySent", false);

		if (username != null)
			searchedReader = readerService.getReaderByUsername(username);

		if (searchedReader != null) {

			photoService.encodeAndResizeReaderPhotoInReader(searchedReader, 80, 80);

			theModel.addAttribute("searchedReader", searchedReader);

			theModel.addAllAttributes(friendsRequestsOptionsModelCreator.createModelMap(readerId, searchedReader.getId()));
		}

		if(username != null && searchedReader == null)
			theModel.addAttribute("notFound",true);
		
		return "addFriends";
	}

}
