package com.thelocalmarketplace.software;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;

import powerutility.PowerGrid;

/*
 * SessionSimulation class contains the main method
 * Controls all the software and runs the simulation of a SelfCheckoutStation Session
 * 
 * Firdovsi Aliyev 30178471
 * Jack Graver 10187274
 * Maheen Nizamani 30172615
 * Minori Olguin 30035923
 * Sarthak Monga 30190643
 * Tanjib Riasat 30170130
 * */

public class SessionSimulation {
	
	private static SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation();

//	public SessionSimulation() {
//		
//	}
//	
//	 Sets up selfCheckoutStation for the Session Simulation
//	 public void setUpSessionSimulation() {
//		selfCheckoutStation.plugIn(PowerGrid.instance());
//	    selfCheckoutStation.turnOn();
//	 }
	  
	 public void promptEnterToContinue(){
			
		 System.out.println("Welcome!");
		 System.out.println("Press \"ENTER\" to continue");
		 try {
			 System.in.read();
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
	 }
	 
	private void printMenu() {
			System.out.print("Choose option:\n"
					+ "\t1. Add Item\n"
					+ "\t2. Pay via Coin\n"
					+ "Choice: ");
	}
	
	public static void main(String[] args) {
		
		SessionSimulation sessionSimulation = new SessionSimulation();
//		sessionSimulation.setUpSessionSimulation();
		
		LocalMarketPlaceDatabase database = new LocalMarketPlaceDatabase();
		
		Barcode milkBarcode = new Barcode(new Numeral[] {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five});
		Barcode juiceBarcode = new Barcode(new Numeral[] {Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.one});
		Barcode breadBarcode = new Barcode( new Numeral[] {Numeral.three, Numeral.four, Numeral.five, Numeral.one, Numeral.two});
		Barcode eggsBarcode = new Barcode(new Numeral[] {Numeral.four, Numeral.five, Numeral.one, Numeral.two, Numeral.three});
		Barcode canOfBeansBarcode = new Barcode(new Numeral[] {Numeral.five, Numeral.one, Numeral.two, Numeral.three, Numeral.four});

		final BarcodedProduct milk = new BarcodedProduct(milkBarcode, "MooMilk 2% 4L", 5L, 4128.00);
		final BarcodedProduct juice = new BarcodedProduct(juiceBarcode, "Orange Juice Pulp Free 2.63L", 7L, 2630.00);
		final BarcodedProduct bread = new BarcodedProduct(breadBarcode, "Whole Wheat Sliced Bread", 3L, 675.00);
		final BarcodedProduct eggs = new BarcodedProduct(eggsBarcode, "Large Eggs, 12 Count", 4L, 699.00);
		final BarcodedProduct canOfBeans = new BarcodedProduct(canOfBeansBarcode, "Dark Red Kidney Beans, 540mL", 2L, 423.00);
			
		database.addBarcodedProductToDatabase(milk);
		database.addBarcodedProductToDatabase(juice);
		database.addBarcodedProductToDatabase(bread);
		database.addBarcodedProductToDatabase(eggs);
		database.addBarcodedProductToDatabase(canOfBeans);
		
		database.addBarcodedProductToInventory(milk, 25);
		database.addBarcodedProductToInventory(juice, 12);
		database.addBarcodedProductToInventory(bread, 35);
		database.addBarcodedProductToInventory(eggs, 44);
		database.addBarcodedProductToInventory(canOfBeans, 75);
		
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
		
		sessionSimulation.printMenu();
		int choice = scanner.nextInt();
		
		switch(choice) {
		case 1:
			System.out.println("Enter barcode to add: ");
//			BigDecimal num = scanner.nextBigDecimal();
			
			Barcode barcode = new Barcode(new Numeral[] {Numeral.two, Numeral.one, Numeral.four, Numeral.three});
			
			if(database.INVENTORY.containsKey(barcode)) {
				System.out.println("Valid Barcode");
			} else {
				System.out.println("Invalid Barcode");
			}

//			selfCheckoutStation.baggingArea.addAnItem(item1);

			break;
		case 2:
			ArrayList<BigDecimal> denoms = (ArrayList<BigDecimal>) selfCheckoutStation.coinDenominations;
			System.out.println("Choose denomination of coin being inserted:");
			for(BigDecimal denom : denoms) {
				System.out.println("\t" + denom);
			}
			BigDecimal denom = scanner.nextBigDecimal();

			int amountDue = 1;
			while(denom != new BigDecimal("-1") && amountDue > 0) {
				if(denoms.contains(denom)) {
					System.out.println("valid denom");
					amountDue -= denom.intValue();
				} else {
					System.out.println("Invalid Denomination amount, please try again");
				}
				denom = scanner.nextBigDecimal();
			}
			break;

		}
		
		scanner.close();
	}
	
}