package com.allbooks.webapp.utils.service;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;

public interface JsonConverter {

	public String getRatingJson(Rating rating);

	public String getReaderJson(Reader readerByUsername);
	
}
