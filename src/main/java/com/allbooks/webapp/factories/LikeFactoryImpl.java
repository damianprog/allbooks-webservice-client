package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Like;
import com.allbooks.webapp.entity.Reader;

@Component
public class LikeFactoryImpl extends LikeFactory{

	@Override
	public Like createInstance(Reader reader) {
		return new Like(reader);
	}

	@Override
	public Like createInstance() {
		return new Like();
	}

	
	
}
