package com.thelocalmarketplace.software;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
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

	private static SelfCheckoutStation selfCheckoutStation;

	private static SessionSimulation sessionSimulation;
	//	sessionSimulation.setUpSessionSimulation();

	private static LocalMarketPlaceDatabase database;
	
	private static Session session;

	private static Scanner scanner;
	
	WeightDiscrepancy discrepancy = new WeightDiscrepancy();

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
		if(session != null && session.getOrderItem().size() != 0) {
			System.out.println("\n============================\n"
								+ "Order Items:");
			int i = 1;
			for(BarcodedItem bi : session.getOrderItem()) {
				System.out.println("\t   " + i + ") " + bi.getBarcode() + " : " + bi.getMass().inGrams() + " gramms");
				i++;
			}
			System.out.println("Total due: " + session.getAmountDue());
		}
		
		System.out.print("\n============================\n"
				+ "Choose option:\n"
				+ "\t 1. Activate Session\n"
				+ "\t 2. Add Item\n"
				+ "\t 3. Pay via Coin\n"
				+ "\t 4. Attendant Screen\n"
				+ "\t-1. Exit\n"
				+ "Choice: ");
	}

	public static void main(String[] args) {

		sessionSimulation = new SessionSimulation();
		
		scanner = new Scanner(System.in);	
		
		SelfCheckoutStation.configureCoinDenominations(new BigDecimal[] {new BigDecimal("0.05"), new BigDecimal("0.10"), new BigDecimal("0.25"), new BigDecimal("1"), new BigDecimal("2")});

		selfCheckoutStation = new SelfCheckoutStation();
		selfCheckoutStation.plugIn(PowerGrid.instance());
		selfCheckoutStation.turnOn();
		
		database = LocalMarketPlaceDatabase.getInstance();


		sessionSimulation.promptEnterToContinue();

		//Ready for more commands from customer
		
		sessionSimulation.printMenu();
		int choice = scanner.nextInt();

		boolean loop = true;
		while(loop) {
			if(session != null && session.hasWeightDiscrepancy()) {
				System.out.print("\n============================\n"
								 + "Weight Discrepancy has been detected\n"
								 + "\t 1. Add/Remove item\n"
								 + "\t 2. Do-Not-Bag Request\n"
								 + "\t 3. Attendant Approval\n"
								 + "\t-1. Exit"
								 + "Choice: ");
				int wChoice = scanner.nextInt();
				switch (wChoice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				}
			} else {
				switch(choice) {
				case 1: //Activate Session
					sessionSimulation.activateSession();
					break;
				case 2: //Add Item
					System.out.print("Enter barcode to add: ");
					BigDecimal barcodeInput = scanner.nextBigDecimal();
	
					String barcodeInputString = barcodeInput.toString();
	
					int i = 0;
					Numeral[] barcodeNumeral = new Numeral[barcodeInputString.length()];
					for(char c : barcodeInputString.toCharArray()) {
						barcodeNumeral[i] = Numeral.valueOf(Byte.valueOf(String.valueOf(c)));
						i++;
					}
					Barcode barcode = new Barcode(barcodeNumeral);
					sessionSimulation.scan(barcode);
					break;
				case 3: //Pay Via Coin
					sessionSimulation.payViaCoin();
					break;
				case 4: //Attendant Screen
					//attendant
					break;
				case -1: //Exit
					System.out.println("Exiting System");
					loop = false;
					System.exit(0);
					break;
				}
				if(!session.hasWeightDiscrepancy()) {
					sessionSimulation.printMenu();
					choice = scanner.nextInt();
				}
			}
		}

		scanner.close();
	}
	
	public void activateSession() {
		session = Session.getInstance();
		if(session.isActive()) {
			System.out.println("A session has already been started, the system cannot start a new session "
							 + "while in an active session.");
		} else {
			session.activate();
			System.out.println("Successfully started a session.");
		}
	}


	public void scan(Barcode barcode) {
		
		//3. Determines the characteristics (weight and cost) of the product associated with the barcode.
		BarcodedProduct product = database.getBarcodedProductToDatabase(barcode);
		if(product != null) {
			System.out.println("The barcode (" + barcode + ") is for " + product.getDescription());
			//5. Signals to the Customer to place the scanned item in the bagging area
			System.out.print("Please place item in the bagging Area (Yes/No): ");
			scanner.nextLine();
			
			String choice = scanner.nextLine().toUpperCase();
			switch(choice) {
			case "YES":
				//4. Updates the expected weight from the bagging area.
				BarcodedItem item = new BarcodedItem(product.getBarcode(), new Mass(product.getExpectedWeight()));
				selfCheckoutStation.baggingArea.addAnItem(item);
				session.newOrderItem(item);
				System.out.println(product.getDescription() + " was added to bagging area");
					//check weight discrepancy
				
				session.addTotalExpectedWeight(product.getExpectedWeight());
				session.addAmountDue(product.getPrice());
				Mass totalExpectedMass = new Mass(session.getTotalExpectedWeight());
				try {
					int diff = totalExpectedMass.inGrams().compareTo(selfCheckoutStation.baggingArea.getCurrentMassOnTheScale().inGrams());
					if(diff != 0) {
						session.setWeightDiscrepancy();
						System.out.println("Weight discrepancy detected");
					}
				} catch (OverloadedDevice e) {
					
				}
				break;
			case "NO":
				if(discrepancy.WeightDiscrepancyMessage(selfCheckoutStation, product) == "Add") {
					item = new BarcodedItem(product.getBarcode(), new Mass(product.getExpectedWeight()));
					selfCheckoutStation.baggingArea.addAnItem(item);
					session.newOrderItem(item);
					System.out.println(product.getDescription() + " was added to bagging area");
						//check weight discrepancy
					
					session.addTotalExpectedWeight(product.getExpectedWeight());
					session.addAmountDue(product.getPrice());
					totalExpectedMass = new Mass(session.getTotalExpectedWeight());
					try {
						int diff = totalExpectedMass.inGrams().compareTo(selfCheckoutStation.baggingArea.getCurrentMassOnTheScale().inGrams());
						if(diff != 0) {
							session.setWeightDiscrepancy();
							System.out.println("Weight discrepancy detected");
						}
					} catch (OverloadedDevice e) {
						
					}
				}
				
				break;
			default:
				System.out.println("Invalid option. " + product.getDescription() + " not added to bagging area");
			}
		} else {
			System.out.println("Invalid Barcode");
		}
	}
	
	
	public void payViaCoin() {
		if(session.getAmountDue() != 0) {
			ArrayList<BigDecimal> denoms = (ArrayList<BigDecimal>) selfCheckoutStation.coinDenominations;
			System.out.println("Choose denomination of coin being inserted:");
			for(BigDecimal denom : denoms) {
				System.out.println("\t" + denom);
			}
			System.out.print("Denomination: ");
			BigDecimal denom = scanner.nextBigDecimal();

			while(denom.compareTo(new BigDecimal("-1")) != 0 && session.getAmountDue() > 0) {
				if(denoms.contains(denom)) {
					session.subAmountDue(denom.intValue());
					if(session.getAmountDue() <= 0) {
						System.out.println("Fully paid amount");
						return;
					}
					System.out.println("Amount due remaining : " + session.getAmountDue());
				} else {
					System.out.println("Invalid Denomination amount, please try again");
				}
				System.out.println("Choose denomination of coin being inserted:");
				for(BigDecimal denom2 : denoms) {
					System.out.println("\t" + denom2);
				}
				System.out.print("Denomination: ");
				denom = scanner.nextBigDecimal();
			}
		} else {
			System.out.println("No amount due");
		}
	}
}