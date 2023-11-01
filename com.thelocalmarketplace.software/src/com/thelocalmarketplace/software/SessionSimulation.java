package com.thelocalmarketplace.software;

import java.io.IOException;
import java.util.Scanner;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

import powerutility.PowerGrid;

public class SessionSimulation {
	
	SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation();

//	public SessionSimulation() {
//		
//	}
	
	// Sets up selfCheckoutStation for the Session Simulation
//	 public void setUpSessionSimulation() {
//		 selfCheckoutStation.plugIn(PowerGrid.instance());
//	     selfCheckoutStation.turnOn();
//	 }
//	 
	 public void promptEnterToContinue(){
			
		 System.out.println("Welcome!");
		 System.out.println("Press \"ENTER\" to continue");
		 try {
			 System.in.read();
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
	 }
	
	public static void main(String[] args) {
		
		SessionSimulation sessionSimulation = new SessionSimulation();
//		sessionSimulation.setUpSessionSimulation();
		
		boolean sessionStart = false;
		StartSession sessionStarted = new StartSession(sessionStart);
		
		Scanner scanner = new Scanner(System.in);		
		
		if (sessionStarted.getSessionStarted() == true) {
			System.out.println("A session has already been started, the system cannot start a new session "
								+ "while in an active session.");
			}
		else {
			sessionSimulation.promptEnterToContinue();
			sessionStarted.setSessionStarted(sessionStart);
			System.out.println("Successfully started a session.");
			}
		
		//Ready for more commands from customer
		
	}
	
}