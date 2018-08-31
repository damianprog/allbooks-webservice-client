package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.factories.ReaderFactory;
import com.allbooks.webapp.security.SecurityContextService;

@Component
public class MainPageModelCreator {

	@Autowired
	private SecurityContextService securityContextService;

	@Autowired
	private LoggedReaderMainPageModelCreator loggedReaderMainPageModelCreator;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReaderFactory readerFactory;

	private ModelMap modelMap;

	private boolean isAuthenticated;

	public ModelMap createModel() {

		initializeThisFields();

		if (!securityContextService.isReaderAuthenticated())
			addReaderAttributeToModel();
		else {
			modelMap.addAllAttributes(loggedReaderMainPageModelCreator.createModel());
			isAuthenticated = true;
		}

		modelMap.addAttribute("isAuthenticated", isAuthenticated);

		return modelMap;
	}

	private void initializeThisFields() {
		this.modelMap = modelMapFactory.createInstance();
		isAuthenticated = false;
	}

	private void addReaderAttributeToModel() {
		Reader reader = readerFactory.createInstance();
		modelMap.addAttribute("reader", reader);

	}

}
