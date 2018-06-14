package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.Reader;

public interface ReaderService {

	public Reader saveReader(Reader reader);

	public Reader getReaderByUsernameAndPassword(String username, String password);

	public Reader getReaderByUsername(String login);

	public void updateReader(Reader reader);

	public boolean isThisLoginTaken(String username);

	public Reader getReaderById(int readerId);

	public int getReaderId(String readerLogin);

	public void deleteReader(int readerId);

	public Reader getReaderByEmail(String email);
}
