package com.thelocalmarketplace.software;
/*
 * StartSession controls whether the SelfCheckoutStation is in an active state ready for customer interaction
 * 
 * Firdovsi Aliyev 30178471
 * Jack Graver 10187274
 * Maheen Nizamani 30172615
 * Minori Olguin 30035923
 * Sarthak Monga 30190643
 * Tanjib Riasat 30170130
 * 
 * */

public class Session {

	private static Session instance = null;
	private boolean isActive = false;
	
	private Session() {
		//instanciate data
	}
	
	public static Session getInstance() {
		if(instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        isActive = false;
    }
	
//	private boolean sessionStarted;
//	
//	// Constructor for StartSession
//	public StartSession(boolean sessionStart) {
//		sessionStarted = sessionStart;
//	}
//	
//	// Returns the value of sessionStarted
//	public boolean getSessionStarted() {
//		return sessionStarted;
//	}
//
//	// Changes that value sessionStarted to the value of sessionStart
//	public void setSessionStarted(boolean sessionStart) {
//		this.sessionStarted = sessionStart;
//	}


}
