package com.thelocalmarketplace.software;

import java.util.HashMap;
import java.util.Map;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
/* 
 * Simple database for the Local Market Place
 * 
 * Firdovsi Aliyev 30178471
 * Jack Graver 10187274
 * Maheen Nizamani 30172615
 * Minori Olguin 30035923
 * Sarthak Monga 30190643
 * Tanjib Riasat 30170130
 * 
 * */

public class LocalMarketPlaceDatabase {
	
	private static LocalMarketPlaceDatabase instance = null;

	private final Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE;
	private final Map<Product, Integer> INVENTORY;

	private LocalMarketPlaceDatabase() {
		BARCODED_PRODUCT_DATABASE = new HashMap<>();
		INVENTORY = new HashMap<>();
		populateDatabase();
	}
	
	private void populateDatabase() {

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
			
		addBarcodedProductToDatabase(milk);
		addBarcodedProductToDatabase(juice);
		addBarcodedProductToDatabase(bread);
		addBarcodedProductToDatabase(eggs);
		addBarcodedProductToDatabase(canOfBeans);

		//		 for (Map.Entry<Barcode, BarcodedProduct> entry : database.BARCODED_PRODUCT_DATABASE.entrySet())  
		//	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 

		addBarcodedProductToInventory(milk, 25);
		addBarcodedProductToInventory(juice, 12);
		addBarcodedProductToInventory(bread, 35);
		addBarcodedProductToInventory(eggs, 44);
		addBarcodedProductToInventory(canOfBeans, 75);
	}

	

	public static LocalMarketPlaceDatabase getInstance() {
		if (instance == null) {
			instance = new LocalMarketPlaceDatabase();
		}
		return instance;
	}

	public void addBarcodedProductToDatabase(BarcodedProduct barcodedProduct) {
		BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
	}
	
	public BarcodedProduct getBarcodedProductToDatabase(Barcode barcode) {
		return BARCODED_PRODUCT_DATABASE.get(barcode);
	}

	public void addBarcodedProductToInventory(BarcodedProduct barcodedProduct, int amount) {
		INVENTORY.put(barcodedProduct, amount);
	}

	public void removeBarcodedProductFromInventory(BarcodedProduct barcodedProduct, int amountRemoved) {
		INVENTORY.put(barcodedProduct, INVENTORY.get(barcodedProduct) - amountRemoved);
	}

	public int getInventoryOfBarcodedProduct(BarcodedProduct barcodedProduct) {
		return INVENTORY.get(barcodedProduct);
	}

	
//	final Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE = new HashMap<>();
//	final Map<Product, Integer> INVENTORY = new HashMap<>();
//	
//	public LocalMarketPlaceDatabase() {
//	}
//
//	public void addBarcodedProductToDatabase(BarcodedProduct barcodedProduct) {
//		BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
//	}
//	
//	public void addBarcodedProductToInventory(BarcodedProduct barcodedProduct, int amount) {
//		INVENTORY.put(barcodedProduct, amount);
//	}
//	
//	public void removeBarcodedProductFromInventory(BarcodedProduct barcodedProduct, int amountRemoved) {
//		INVENTORY.put(barcodedProduct, INVENTORY.get(barcodedProduct) - amountRemoved);
//	}
//	
//	// Returns an integer representing the amount of inventory
//	public int getInventoryOfBarcodedProduct(BarcodedProduct barcodedProduct) {
//		return INVENTORY.get(barcodedProduct);
//	}
}
