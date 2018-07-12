package com.allbooks.webapp.service;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Ban;

public interface BanService {

	Page<Ban> getBans(int page);
	
	void saveBan(Ban ban);
	
	Ban getBanById(int banId);
	
	Ban getBanByReaderId(int readerId);
	
	void deleteBanById(int banId);

	void deleteBanByReaderId(int readerId);
	
}
