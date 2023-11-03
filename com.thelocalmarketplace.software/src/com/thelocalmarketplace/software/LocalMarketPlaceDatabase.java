package com.thelocalmarketplace.software;

import java.util.HashMap;
import java.util.Map;

import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import ca.ucalgary.seng300.simulation.SimulationException;

public class LocalMarketPlaceDatabase {
	
	final Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE = new HashMap<>();
	final Map<Product, Integer> INVENTORY = new HashMap<>();
	
	public LocalMarketPlaceDatabase() {
	}
	
	public void addBarcodedProductToDatabase(BarcodedProduct barcodedProduct) {
		BARCODED_PRODUCT_DATABASE.put(barcodedProduct.getBarcode(), barcodedProduct);
	}
	
	public void addBarcodedProductToInventory(BarcodedProduct barcodedProduct, int amount) {
		INVENTORY.put(barcodedProduct, amount);
	}
	
	public void removeBarcodedProductFromInventory(BarcodedProduct barcodedProduct, int amount) {
		INVENTORY.put(barcodedProduct, INVENTORY.get(amount)-1);
	}
	
	public int getInventoryOfBarcodedProduct(BarcodedProduct barcodedProduct, int amount) {
		return INVENTORY.get(barcodedProduct);
	}
}
