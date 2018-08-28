package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;
import com.allbooks.webapp.factories.ReaderRoleFactory;
import com.allbooks.webapp.webservice.ReaderWebservice;

@Component
public class ReaderRoleSaver {

	@Autowired
	private ReaderWebservice readerWebservice;

	@Autowired
	private ReaderRoleFactory readerRoleFactory;
	
	public void save(Reader reader) {

		ReaderRole readerRole = readerRoleFactory.createInstance();

		readerRole.setReaderId(reader.getId());
		readerRole.setRoleId(2);
		readerWebservice.saveReaderRole(readerRole);

	}

}
