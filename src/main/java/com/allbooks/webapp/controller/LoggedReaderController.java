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
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FavoriteGenresService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.model.ReadingChallangeModelCreator;
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
	private SecurityContextService contextService;

	@Autowired
	private FavoriteGenresService favoriteGenresService;

	@Autowired
	private ReadingChallangeModelCreator readingChallangeModelCreator;

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

	@GetMapping("/submitReadingChallange")
	public String submitReadingChallange(Model theModel,RedirectAttributes ra, @RequestParam("numberOfBooks") int numberOfBooks,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = new ReadingChallange(reader, numberOfBooks);

		readingChallangeService.saveReadingChallange(readingChallange);

		ra.addAttribute("readerId",readerId);
		return "redirect:/loggedReader/showReadingChallange";
	}

	@GetMapping("/showReadingChallange")
	public String showReadingChallange(Model theModel, @RequestParam("readerId") int readerId) {

		theModel.addAllAttributes(readingChallangeModelCreator.createModel(readerId));

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

	
	
}
