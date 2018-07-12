package com.allbooks.webapp.utils.reader;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.service.BanService;

@Component
public class BanChecker {

	@Autowired
	private BanService banService;
	
	public boolean isReaderBanned(int readerId) {
		
		Ban ban = banService.getBanByReaderId(readerId);
		
		if(ban != null) {
			
			if(ban.getExpiryDate().getTime() - new Date().getTime() <= 0) {
				banService.deleteBanById(ban.getId());
				return false;
			}
			else {
				return true;
			}
			
		}
		
		return false;
	}
	
}
