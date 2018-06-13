package com.allbooks.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;

@Component
public class ReaderSaver {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ReaderService readerService;

	public void save(Reader reader) {

		Reader readerPass = readerService.getReaderById(reader.getId());
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(readerPass));

	}
}
