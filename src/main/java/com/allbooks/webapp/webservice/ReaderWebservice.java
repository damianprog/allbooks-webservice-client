package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderRole;
import com.allbooks.webapp.entity.Review;

public interface ReaderWebservice {

	public void saveReader(Reader theReader);

	public Reader getReaderByUsernameAndPassword(String login, String password);

	public Reader getReaderByUsername(String login);

	public void updateReader(Reader reader);

	public Reader getReaderById(int readerId);

	public void deleteReader(int readerId);

	public Reader getReaderByEmail(String email);

	public void saveReaderRole(ReaderRole readerRole);

}
