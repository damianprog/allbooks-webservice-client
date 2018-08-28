package com.allbooks.webapp.utils.saver;

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

	public Reader save(Reader reader) {

		Reader readerPass = readerService.saveReader(reader);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(readerPass));
		return readerPass;
	}
}
