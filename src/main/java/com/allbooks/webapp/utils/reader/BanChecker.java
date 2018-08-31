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

	boolean isReaderBanned = false;

	public boolean isReaderBanned(int readerId) {

		checkIfReaderIsBanned(readerId);

		return isReaderBanned;
	}

	private void checkIfReaderIsBanned(int readerId) {
		Ban ban = banService.getBanByReaderId(readerId);

		if (ban != null)
			checkBanValidity(ban);
	}

	private void checkBanValidity(Ban ban) {
		if (isBanExpired(ban)) {
			banService.deleteBanById(ban.getId());
		} else {
			isReaderBanned = true;;
		}
	}

	private boolean isBanExpired(Ban ban) {
		return ban.getExpiryDate().getTime() - new Date().getTime() <= 0;
	}

}
