package com.allbooks.webapp.utils.model;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.readerbook.ReaderBooksForMyBooksGetter;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;

@Component
public class MyBooksModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;

	@Autowired
	private ReaderBooksForMyBooksGetter readerBooksForMyBooksGetter;

	@Autowired
	private ReaderService readerService;

	private ModelMap modelMap;

	private Reader reader;

	private ShelvesState shelvesState;

	private int page;

	public ModelMap create(int readerId, int page, String shelves) {

		initializeThisFields(readerId, page, shelves);

		initializeReaderBooksQuantitiesModel();

		initializeReaderBooksPage();

		modelMap.addAttribute("reader", reader);
		modelMap.addAttribute("shelvesStates", shelvesState);
		modelMap.addAttribute("page", page);

		return modelMap;
	}

	private void initializeThisFields(int readerId, int page, String shelves) {
		this.modelMap = modelMapFactory.createInstance();
		this.reader = readerService.getReaderById(readerId);
		this.page = page;
		this.shelvesState = ShelvesState.enumValueOf(shelves);
	}

	private void initializeReaderBooksQuantitiesModel() {

		Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService
				.getReaderBooksQuantities(reader.getId());

		modelMap.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		modelMap.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		modelMap.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
		modelMap.addAttribute("all", readerBooksQuantitiesMap.get("all"));
	}

	private void initializeReaderBooksPage() {

		Page<ReaderBook> readerBooksPage = readerBooksForMyBooksGetter.getPreparedReaderBooks(reader.getId(),
				shelvesState, page);

		modelMap.addAttribute("readerBooks", readerBooksPage.getContent());
		modelMap.addAttribute("readerBooksPage", readerBooksPage);
	}

}
