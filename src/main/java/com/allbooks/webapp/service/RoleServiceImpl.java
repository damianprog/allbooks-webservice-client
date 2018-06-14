package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Role;
import com.allbooks.webapp.webservice.RoleWebservice;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleWebservice roleWebservice;
	
	@Override
	public Role getRoleById(int roleId) {
		return roleWebservice.getRoleById(roleId);
	}

}
