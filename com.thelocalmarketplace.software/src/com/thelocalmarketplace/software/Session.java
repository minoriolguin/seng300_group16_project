package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

import powerutility.PowerGrid;

public class Session {
	SelfCheckoutStation selfCheckoutStation;
	private boolean start;

	public Session(boolean start) {
		selfCheckoutStation = new SelfCheckoutStation();
		
		selfCheckoutStation.plugIn(PowerGrid.instance());
		selfCheckoutStation.turnOn();
		
		this.start = start;
	}
	
	public void setSession(boolean start) {
		this.start = start;
	}
	
	public boolean getSession() {
		return this.start;	
	}
	
	public static void main(String[] args) {
		
		boolean start = false;
		Session sessionStarted = new Session(start);

		if (sessionStarted.getSession() == true) {
			System.out.println("A session has already been started, "
					+ "the system cannot start a new session while in "
					+ "an active session.");
		}
		else {
			sessionStarted.setSession(true);
			System.out.println("Successfully started a session.");
		}
		
	}
	
}
