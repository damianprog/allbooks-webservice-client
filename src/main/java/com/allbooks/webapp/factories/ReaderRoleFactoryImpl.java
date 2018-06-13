package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderRole;

@Component
public class ReaderRoleFactoryImpl extends ReaderRoleFactory{

	public ReaderRole createInstance() {
		
		return new ReaderRole();
		
	}
	
}
