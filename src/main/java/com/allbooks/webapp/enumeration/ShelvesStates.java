package com.allbooks.webapp.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ShelvesStates {

	READ("Read"), CURRENTLY_READING("Currently Reading"), WANT_TO_READ("Want To Read");

	private String shelveState;

	private static Map<String, ShelvesStates> map = new HashMap<>();
	
	 static {
	        for (ShelvesStates state : ShelvesStates.values()) {
	            map.put(state.shelveState, state);
	        }
	    }
	
	ShelvesStates(String shelveState){
		this.shelveState = shelveState;
	}

	public String shelveState() {
		return shelveState;
	}

	public static ShelvesStates enumValueOf(String shelveState) {
        return map.get(shelveState);
    }
	
}
