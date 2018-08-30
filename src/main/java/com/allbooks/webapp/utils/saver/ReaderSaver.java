package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderSaver {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private ReaderRoleSaver readerRoleSaver;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Reader save(Reader reader) {

		photoService.createDefaultPhotoForReader(reader);
		
		reader.setPassword(bCryptPasswordEncoder.encode(reader.getPassword()));

		Reader savedReader = readerService.saveReader(reader);
		
		readerRoleSaver.save(savedReader);
		
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(savedReader));
		
		return savedReader;
	}
}
