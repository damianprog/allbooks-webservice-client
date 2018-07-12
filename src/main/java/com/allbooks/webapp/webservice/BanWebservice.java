package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Ban;

public interface BanWebservice {

	void saveBan(Ban ban);

	Ban getBanById(int banId);

	Ban getBanByReaderId(int readerId);

	void deleteBanById(int banId);

	void deleteBanByReaderId(int readerId);

	Page<Ban> getBans(int page);

}
