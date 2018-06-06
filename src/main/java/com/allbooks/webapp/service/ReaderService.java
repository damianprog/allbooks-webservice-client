package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;

public interface ReaderService {

	public boolean saveReader(Reader theReader);

	public Reader getReaderByUsernameAndPassword(String username, String password);

	public Reader getReaderByUsername(String login);

	public void updateReader(Reader reader);

	public boolean checkReaderLogin(String username);

	public Reader getReaderById(int readerId);

	public int getReaderId(String readerLogin);

	public void deleteReader(int readerId);

	public Reader getReaderByEmail(String email);
}
