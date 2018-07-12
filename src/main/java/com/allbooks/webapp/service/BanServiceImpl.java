package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.webservice.BanWebservice;

@Service
public class BanServiceImpl implements BanService {

	@Autowired
	private BanWebservice banWebservice;

	@Override
	public void saveBan(Ban ban) {
		banWebservice.saveBan(ban);
	}

	@Override
	public Ban getBanById(int banId) {
		return banWebservice.getBanById(banId);
	}

	@Override
	public Ban getBanByReaderId(int readerId) {
		return banWebservice.getBanByReaderId(readerId);
	}

	@Override
	public void deleteBanById(int banId) {
		banWebservice.deleteBanById(banId);
	}

	@Override
	public void deleteBanByReaderId(int readerId) {
		banWebservice.deleteBanByReaderId(readerId);
	}

	@Override
	public Page<Ban> getBans(int page) {
		return banWebservice.getBans(page);
	}

}
