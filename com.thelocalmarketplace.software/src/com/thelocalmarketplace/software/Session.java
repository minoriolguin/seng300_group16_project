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
	private static ArrayList<BarcodedItem> orderItems;
	private static double totalExpectedWeight;
	private static double amountDue;
	
	private Session() {
		//Instantiate data
		orderItems = new ArrayList<BarcodedItem>();
		totalExpectedWeight = 0;
		amountDue = 0;
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
	
    public ArrayList<BarcodedItem> getOrderItem() {
    	return orderItems;
    }
    
    public void newOrderItem(BarcodedItem item) {
    	orderItems.add(item);
    }
    
    public double getTotalExpectedWeight() {
    	return totalExpectedWeight;
    }
    
    public void addTotalExpectedWeight(double weight) {
    	totalExpectedWeight += weight;
    }
    
    public double getAmountDue() {
    	return amountDue;
    }
    
    public void addAmountDue(double amount) {
    	amountDue += amount;
    }
    
    public void subAmountDue(double amount) {
    	amountDue -= amount;
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
