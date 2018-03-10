package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;

public interface TokenService {

	public void createPasswordToken(Reader reader, String token);

	public PasswordToken getPasswordTokenByReaderId(int readerId);

	public PasswordToken getPasswordTokenByCredentials(int readerId, String token);

	public void deletePasswordToken(int readerId);

	public void createVerificationToken(Reader reader, String verificationToken);

	public VerificationToken getTokenByReaderId(int readerId);

	public void deleteVerificationTokenTokenById(int tokenId);

}
