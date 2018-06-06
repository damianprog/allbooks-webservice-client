package com.allbooks.webapp.utils.service;

import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//extend book related objects

@Service
public class JsonConverterImpl implements JsonConverter{

	@Override
	public String getRatingJson(Rating rating) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(rating);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getReaderJson(Reader reader) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(mapper);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
