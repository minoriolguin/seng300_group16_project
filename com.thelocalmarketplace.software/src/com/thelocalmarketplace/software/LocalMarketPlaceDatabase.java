package com.thelocalmarketplace.software;

import java.util.HashMap;
import java.util.Map;

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
	
	public void removeBarcodedProductFromInventory(BarcodedProduct barcodedProduct, int amountRemoved) {
		INVENTORY.put(barcodedProduct, INVENTORY.get(barcodedProduct) - amountRemoved);
	}
	
	// Returns an integer representing the amount of inventory
	public int getInventoryOfBarcodedProduct(BarcodedProduct barcodedProduct) {
		return INVENTORY.get(barcodedProduct);
	}
}
