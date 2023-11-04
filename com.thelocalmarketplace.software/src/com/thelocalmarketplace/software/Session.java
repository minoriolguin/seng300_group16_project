package com.thelocalmarketplace.software;

import java.util.ArrayList;

import com.jjjwelectronics.scanner.BarcodedItem;

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
	private static ArrayList<BarcodedItem> scannedBarcodedItems;
	
	private Session() {
		//instanciate data
		scannedBarcodedItems = new ArrayList<BarcodedItem>();
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
	
    public ArrayList<BarcodedItem> getScannedBarcodedItems() {
    	return scannedBarcodedItems;
    }
    
    public void newScannedBarcodedItem(BarcodedItem item) {
    	scannedBarcodedItems.add(item);
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
