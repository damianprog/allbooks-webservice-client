package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReaderService;

@Component
public class AddFriendsModelCreator {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private FriendsRequestsOptionsModelCreator friendsRequestsOptionsModelCreator;

	private Reader searchedReader;

	private ModelMap modelMap;
	
	public ModelMap createModel(String username) {

		initializeModelMap();

		initializeSearchedReaderIfUsernameIsPresent(username);
		
		initializeSearchedReaderFriendsInfoModel();

		checkIfReaderWasntFound(username);

		return modelMap;
	}

	private void initializeModelMap() {
		this.modelMap = modelMapFactory.createInstance();
	}
	
	private void initializeSearchedReaderIfUsernameIsPresent(String username) {
		if (username != null)
			searchedReader = readerService.getReaderByUsername(username);
	}
	
	private void initializeSearchedReaderFriendsInfoModel() {

		if (searchedReader != null) {

			modelMap.addAttribute("searchedReader", searchedReader);

			modelMap.addAllAttributes(
					friendsRequestsOptionsModelCreator.createModelMap(searchedReader.getId()));
		}
	}

	private void checkIfReaderWasntFound(String username) {
		if (username != null && searchedReader == null)
			modelMap.addAttribute("notFound", true);
	}
	
}
