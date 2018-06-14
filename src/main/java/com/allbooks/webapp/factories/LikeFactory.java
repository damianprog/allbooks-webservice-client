package com.allbooks.webapp.factories;

import com.allbooks.webapp.entity.Like;
import com.allbooks.webapp.entity.Reader;

public abstract class LikeFactory {

	public abstract Like createInstance(Reader reader);
	
	public abstract Like createInstance();
	
}
