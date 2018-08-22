package com.allbooks.webapp.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ShelvesState {

	READ("Read"), CURRENTLY_READING("Currently Reading"), WANT_TO_READ("Want To Read");

	private String shelveState;

	private static Map<String, ShelvesState> map = new HashMap<>();
	
	 static {
	        for (ShelvesState state : ShelvesState.values()) {
	            map.put(state.shelveState, state);
	        }
	    }
	
	ShelvesState(String shelveState){
		this.shelveState = shelveState;
	}

	public String shelveState() {
		return shelveState;
	}

	public static ShelvesState enumValueOf(String shelveState) {
        return map.get(shelveState);
    }
	
}
