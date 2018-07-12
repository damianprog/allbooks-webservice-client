package com.allbooks.webapp.utils.admin;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BanService;
import com.allbooks.webapp.utils.reader.BanChecker;
import com.allbooks.webapp.utils.service.EmailService;

@Component
public class BanningExecutor {

	@Autowired
	private BanChecker banChecker;
	
	@Autowired
	private BanService banService;
	
	@Autowired
	private EmailService emailService;
	
	public void ban(Reader reader,Ban ban) throws MessagingException {
		
		if(banChecker.isReaderBanned(reader.getId()))
			banService.deleteBanByReaderId(reader.getId());
		
		ban.setReader(reader);

		emailService.sendBanInformation(ban);

		banService.saveBan(ban);
		
	}
	
}
