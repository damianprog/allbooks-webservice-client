package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public class ModelMapFactoryImpl extends ModelMapFactory{

	@Override
	public ModelMap createInstance() {
		return new ModelMap();
	}

}
