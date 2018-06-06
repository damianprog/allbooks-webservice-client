package com.allbooks.webapp.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Role;
import com.allbooks.webapp.service.ReaderService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Reader reader;

		boolean isUserInDb = readerService.checkReaderLogin(username);

		if (isUserInDb == true) {
			reader = readerService.getReaderByUsername(username);

			if (reader.isEnabled() == false)
				throw new UsernameNotFoundException(username);
			else {
				Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
				for (Role role : reader.getRoles()) {

					grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
				}

				httpSession.setAttribute("readerId", reader.getId());
				httpSession.setAttribute("IS_USER_LOGGED", true);				
				
				return new org.springframework.security.core.userdetails.User(reader.getUsername(),
						reader.getPassword(), grantedAuthorities);
			}
		} else
			throw new UsernameNotFoundException(username);
	}
}
