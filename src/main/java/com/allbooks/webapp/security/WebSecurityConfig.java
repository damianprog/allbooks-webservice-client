package com.allbooks.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("**/main/").permitAll()
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.antMatchers("/bookPageActions/**").hasAuthority("READER")
		.antMatchers("/loggedReader/**").hasAuthority("READER")
		.antMatchers("/friends/**").hasAuthority("READER")
		.antMatchers("/profile/showProfile").permitAll()
		.antMatchers("/profile/**").hasAuthority("READER")
		.and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/visitor/main")
        .permitAll()
        .and()
        .rememberMe()
        	.key("uniqueAndSecret")
        	.rememberMeCookieName("allbooks-remember-me")
        	.tokenValiditySeconds(24 * 60 * 60)
        .and()
        .logout().logoutSuccessUrl("/visitor/main")
        .and()
        .exceptionHandling().accessDeniedPage("/readerAccount/accessDenied");
	

		http.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}