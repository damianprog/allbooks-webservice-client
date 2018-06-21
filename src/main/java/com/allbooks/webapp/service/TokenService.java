package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;

public interface TokenService {

	public PasswordToken createPasswordToken(Reader reader, String token);

	public PasswordToken getPasswordTokenByReaderId(int readerId);

	public PasswordToken getPasswordTokenByCredentials(int readerId, String token);

	public void deletePasswordTokenByReaderId(int readerId);

	public VerificationToken createVerificationToken(Reader reader, String verificationToken);

	public VerificationToken getVerificationTokenByReaderId(int readerId);

	public void deleteVerificationTokenById(int tokenId);

	public void updateVerificationToken(VerificationToken verificationToken);
	
}
