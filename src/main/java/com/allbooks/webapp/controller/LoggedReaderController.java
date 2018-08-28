package com.allbooks.webapp.controller;

import java.security.Principal;
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

import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.utils.FavouriteGenresJsonArrayGetter;
import com.allbooks.webapp.utils.model.ReadingChallangeModelCreator;
import com.allbooks.webapp.utils.service.SaveService;

@Controller
@RequestMapping("/loggedReader")
public class LoggedReaderController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ReadingChallangeModelCreator readingChallangeModelCreator;

	@Autowired
	private FavouriteGenresJsonArrayGetter favouriteGenresJsonArrayGetter;

	@Autowired
	private SaveService saveService;

	@GetMapping("/showNotifications")
	public String showNotificationsPage(Model theModel, Principal principal, HttpSession session,
			@RequestParam(defaultValue = "1") int page) {

		int readerId = contextService.getLoggedReaderId();

		Page<Notification> notifications = notificationService.getNotificationsByReaderId(readerId, page);

		theModel.addAttribute("notifications", notifications.getContent());
		theModel.addAttribute("notificationsPage", notifications);
		theModel.addAttribute("currentPage", page);

		return "reader/notifications";

	}

	@GetMapping("/deleteNotification")
	public String deleteNotification(Model theModel, @RequestParam("notificationId") int notificationId,
			HttpSession session) {

		int readerId = contextService.getLoggedReaderId();

		notificationService.deleteNotificationByIdAndReaderId(notificationId, readerId);

		return "redirect:/loggedReader/showNotifications";
	}

	@GetMapping("/submitReadingChallange")
	public String submitReadingChallange(Model theModel, RedirectAttributes ra,
			@RequestParam("numberOfBooks") int numberOfBooks, HttpSession session) {
		
		saveService.saveReadingChallangeByNumberOfBooks(numberOfBooks);
		
		ra.addAttribute("readerId", contextService.getLoggedReaderId());
		return "redirect:/loggedReader/showReadingChallange";
	}

	@GetMapping("/showReadingChallange")
	public String showReadingChallange(Model theModel, @RequestParam("readerId") int readerId) {

		theModel.addAllAttributes(readingChallangeModelCreator.createModel(readerId));

		return "reader/readingChallange";
	}

	@GetMapping("/showFavouriteGenres")
	public String showFavoriteGenres(Model theModel,
			@RequestParam(value = "savedAlert", defaultValue = "false") boolean savedAlert) {

		theModel.addAllAttributes(favouriteGenresJsonArrayGetter.get());

		theModel.addAttribute("savedAlert", savedAlert);

		return "reader/favouriteGenres";
	}

	@PostMapping("/saveFavouriteGenres")
	public String saveFavoriteGenres(Model theModel, RedirectAttributes ra,
			@RequestParam("categoryChecked") List<String> categoriesChecked) {

		saveService.saveFavouriteGenresByCheckedGenresList(categoriesChecked);

		ra.addAttribute("savedAlert", true);

		return "redirect:/loggedReader/showFavouriteGenres";

	}

}
