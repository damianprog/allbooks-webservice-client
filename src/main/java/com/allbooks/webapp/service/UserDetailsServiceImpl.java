package com.allbooks.webapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Role;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	ReaderService readerService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Reader reader = readerService.getReaderByUsername(username);
		 System.out.println("USRNAME: " + reader.getUsername() + " PASSWORD: " + reader.getPassword());
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
	        for (Role role : reader.getRoles()){
	            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
	        }
	        
	        return new org.springframework.security.core.userdetails.User(reader.getUsername(), reader.getPassword(), grantedAuthorities);
	    }
	}


