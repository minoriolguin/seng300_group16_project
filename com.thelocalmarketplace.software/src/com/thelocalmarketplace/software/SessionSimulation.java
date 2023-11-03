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
	private static ArrayList<BarcodedItem> scannedBarcodedItems = new ArrayList<BarcodeItem>();
	private static double totalExpectedWeight = 0;
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
				//						3. Attendant
				+ "Choice: ");
	}

	//	printAttendantMenu() {
	//		Heres all the blocks
	//	}

	private void setupDatabase() {
		database = new LocalMarketPlaceDatabase();

		Barcode milkBarcode = new Barcode(new Numeral[] {Numeral.one, Numeral.two, Numeral.three, Numeral.four, Numeral.five});
		Barcode juiceBarcode = new Barcode(new Numeral[] {Numeral.two, Numeral.three, Numeral.four, Numeral.five, Numeral.one});
		Barcode breadBarcode = new Barcode( new Numeral[] {Numeral.three, Numeral.four, Numeral.five, Numeral.one, Numeral.two});
		Barcode eggsBarcode = new Barcode(new Numeral[] {Numeral.four, Numeral.five, Numeral.one, Numeral.two, Numeral.three});
		Barcode canOfBeansBarcode = new Barcode(new Numeral[] {Numeral.five, Numeral.one, Numeral.two, Numeral.three, Numeral.four});

		final BarcodedProduct milk = new BarcodedProduct(milkBarcode, "MooMilk 2% 4L", 5_59L, 4128.00);
		final BarcodedProduct juice = new BarcodedProduct(juiceBarcode, "Orange Juice Pulp Free 2.63L", 6_99L, 2630.00);
		final BarcodedProduct bread = new BarcodedProduct(breadBarcode, "Whole Wheat Sliced Bread", 2_75L, 675.00);
		final BarcodedProduct eggs = new BarcodedProduct(eggsBarcode, "Large Eggs, 12 Count", 3_29L, 699.00);
		final BarcodedProduct canOfBeans = new BarcodedProduct(canOfBeansBarcode, "Dark Red Kidney Beans, 540mL", 1_78L, 423.00);
			
		database.addBarcodedProductToDatabase(milk);
		database.addBarcodedProductToDatabase(juice);
		database.addBarcodedProductToDatabase(bread);
		database.addBarcodedProductToDatabase(eggs);
		database.addBarcodedProductToDatabase(canOfBeans);

		//		 for (Map.Entry<Barcode, BarcodedProduct> entry : database.BARCODED_PRODUCT_DATABASE.entrySet())  
		//	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 

		database.addBarcodedProductToInventory(milk, 25);
		database.addBarcodedProductToInventory(juice, 12);
		database.addBarcodedProductToInventory(bread, 35);
		database.addBarcodedProductToInventory(eggs, 44);
		database.addBarcodedProductToInventory(canOfBeans, 75);
	}


	public static void main(String[] args) {

		sessionSimulation = new SessionSimulation();
		selfCheckoutStation = new SelfCheckoutStation();

		sessionSimulation.setupDatabase();

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

		sessionSimulation.printMenu();
		int choice = scanner.nextInt();

		while(choice != -1) {
			/*
			 * Weight discrepency at the top of this to stop any more execution?? maybe somewhere else to allow some 
			 * menu items to execute
			 */
			switch(choice) {
			case 1:
				System.out.println("Enter barcode to add: ");
				BigDecimal barcodeInput = scanner.nextBigDecimal();

				System.out.println("You entered: " + barcodeInput);
				String barcodeInputString = barcodeInput.toString();

				int i = 0;
				Numeral[] barcodeNumeral = new Numeral[barcodeInputString.length()];
				for(char c : barcodeInputString.toCharArray()) {
					barcodeNumeral[i] = Numeral.valueOf(Byte.valueOf(String.valueOf(c)));
					i++;
				}

				Barcode barcode = new Barcode(barcodeNumeral);
				sessionSimulation.scan(barcode);
				//scannedBarcodedItems.add(database.BARCODED_PRODUCT_DATABASE.get(barcode));
				selfCheckoutStation.baggingArea.addAnItem(new BarcodedItem(barcode,new Mass(database.BARCODED_PRODUCT_DATABASE.get(barcode).getExpectedWeight())));
				totalExpectedWeight += database.BARCODED_PRODUCT_DATABASE.get(barcode).getExpectedWeight();
			    Mass totalExpectedMass = new Mass(totalExpectedWeight);
				if( totalExpectedMass != selfCheckoutStation.baggingArea.getCurrentMassOnTheScale()) {
					// Should call WeightDiscrepancy();
				}

				//				if(database.INVENTORY.containsKey(barcode)) {
				//					System.out.println("Valid Barcode");
				//				} else {
				//					System.out.println("Invalid Barcode");
				//				}


				//				selfCheckoutStation.baggingArea.addAnItem(item1);

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
			case -1:
				System.out.println("Exiting System");
			}
			choice = scanner.nextInt();
		}

		scanner.close();
	}


	public boolean scan(Barcode barcode) {
		if(barcode == null) {
			throw new NullPointerException("No argument may be null.");
		}
		//		if(mass <= 0.0) {
		//			throw new IllegalArgumentException("The weight of the item should be greater than 0.0.");
		//		}
		
		BarcodedProduct scannedProduct = database.BARCODED_PRODUCT_DATABASE.get(barcode);
		
//		if(database.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
		if(scannedProduct != null) {
			selfCheckoutStation.scanner.disable(); //  System: Blocks the self-checkout station from further customer interaction.
		
			int inventoryLeft = database.INVENTORY.get(scannedProduct); 
			if(inventoryLeft == 0) {
				return false;
			} else {
//				BarcodedProduct p = database.BARCODED_PRODUCT_DATABASE.get(barcode);

				BarcodedItem scannedItem = new BarcodedItem(scannedProduct.getBarcode(), new Mass(scannedProduct.getExpectedWeight()));

				selfCheckoutStation.baggingArea.addAnItem(scannedItem);

				try {
					System.out.println("\tBaggingArea weight: " + selfCheckoutStation.baggingArea.getCurrentMassOnTheScale());
				} catch (OverloadedDevice e) {
					e.printStackTrace();
				}

				selfCheckoutStation.scanner.scan(scannedItem);

			}
			selfCheckoutStation.scanner.enable();
		} else {
			return false;
		}
		return false;

	}
}