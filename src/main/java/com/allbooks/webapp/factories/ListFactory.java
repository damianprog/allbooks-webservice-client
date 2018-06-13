package com.allbooks.webapp.factories;

import java.util.List;

public abstract class ListFactory {

	public abstract <T> List<T> createArrayList();
	
}