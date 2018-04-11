package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.VerificationToken;

public interface TokenWebservice {

	public void savePasswordToken(PasswordToken passwordToken);

	public PasswordToken getPasswordTokenByReaderId(int readerId);

	public PasswordToken getPasswordTokenByCredentials(int readerId, String token);

	public void deletePasswordTokenByReaderId(int readerId);

	public void saveVerificationToken(VerificationToken verificationToken);

	public VerificationToken getVerificationTokenByReaderId(int readerId);

	public void deleteVerificationTokenTokenById(int tokenId);
	
}
