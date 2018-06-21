package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.VerificationToken;

public interface TokenWebservice {

	public PasswordToken savePasswordToken(PasswordToken passwordToken);

	public PasswordToken getPasswordTokenByReaderId(int readerId);

	public PasswordToken getPasswordTokenByCredentials(int readerId, String token);

	public void deletePasswordTokenByReaderId(int readerId);

	public VerificationToken saveVerificationToken(VerificationToken verificationToken);

	public VerificationToken getVerificationTokenByReaderId(int readerId);

	public void deleteVerificationTokenTokenById(int tokenId);

	public void updateVerificationToken(VerificationToken verificationToken);
	
}
