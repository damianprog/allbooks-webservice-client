package com.allbooks.webapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.reader.ReaderRoleSaver;
import com.allbooks.webapp.webservice.ReaderWebservice;

@Service
public class ReaderServiceImpl implements ReaderService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ReaderWebservice readerWebservice;

	@Autowired
	private ReaderRoleSaver readerRoleSaver;
	
	@Override
	public Reader saveReader(Reader reader) {

		reader.setPassword(bCryptPasswordEncoder.encode(reader.getPassword()));

		Reader savedReader = readerWebservice.saveReader(reader);
		
		readerRoleSaver.save(savedReader);
		
		return savedReader;

	}

	@Override
	public Reader getReaderByUsername(String login) {

		return readerWebservice.getReaderByUsername(login);
	}

	@Override
	public boolean isThisLoginTaken(String username) {

		Reader reader = readerWebservice.getReaderByUsername(username);

		if (reader == null)
			return false;
		else
			return true;
	}

	@Override
	public Reader getReaderByUsernameAndPassword(String login, String password) {

		Reader reader = readerWebservice.getReaderByUsernameAndPassword(login, password);

		if (reader != null) {
			if (reader.getPassword().equals(password))
				return reader;
			else
				return null;
		} else
			return null;

	}

	@Override
	public void updateReader(Reader reader) {
		readerWebservice.updateReader(reader);

	}

	@Override
	public Reader getReaderById(int readerId) {

		return readerWebservice.getReaderById(readerId);
	}

	@Override
	public int getReaderId(String readerLogin) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", String.valueOf(readerLogin));

		Reader reader = readerWebservice.getReaderByUsername(readerLogin);

		return reader.getId();
	}

	@Override
	public void deleteReader(int readerId) {
		readerWebservice.deleteReader(readerId);

	}

	@Override
	public Reader getReaderByEmail(String email) {

		return readerWebservice.getReaderByEmail(email);
	}

}
