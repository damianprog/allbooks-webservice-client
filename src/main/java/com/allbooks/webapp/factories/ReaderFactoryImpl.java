package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;

@Component
public class ReaderFactoryImpl extends ReaderFactory{

	@Override
	public Reader createInstance() {
		return new Reader();
	}

}
