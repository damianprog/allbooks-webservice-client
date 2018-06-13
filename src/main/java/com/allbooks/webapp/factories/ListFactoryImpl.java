package com.allbooks.webapp.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ListFactoryImpl extends ListFactory{

	@Override
	public <T> List<T> createArrayList() {
		
		return new ArrayList<T>(); 
		
	}

}
