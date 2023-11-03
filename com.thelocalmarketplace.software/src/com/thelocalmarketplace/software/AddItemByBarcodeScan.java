package com.thelocalmarketplace.software;

import java.util.Map;
import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.LocalMarketPlaceDatabase;
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

public class AddItemByBarcodeScan implements BarcodeScannerListener{
	LocalMarketPlaceDatabase database = new LocalMarketPlaceDatabase();
	
	public boolean Scan(Barcode barcode,long mass,SelfCheckoutStation station) {            
		
		if(barcode == null) {
			throw new NullPointerException("No argument may be null.");
		}
		if(mass <= 0.0) {
			throw new IllegalArgumentException("The weight of the item should be greater than 0.0.");
		}
		if(database.BARCODED_PRODUCT_DATABASE.containsKey(barcode)) {
			station.scanner.disable(); //  System: Blocks the self-checkout station from further customer interaction.
			int inventoryLeft = database.INVENTORY.get(barcode); 
			if(inventoryLeft == 0) {
				return false;
			} else {
				BarcodedProduct p = database.BARCODED_PRODUCT_DATABASE.get(barcode);
				
				BarcodedItem i = new BarcodedItem(p.getBarcode(), null);
				
				station.baggingArea.addAnItem(i);
				
				try {
					System.out.println("\tBaggingArea weight: " + station.baggingArea.getCurrentMassOnTheScale());
				} catch (OverloadedDevice e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				station.scanner.scan(i);
				
			}
			
		} else {
			System.out.println("no has");
			return false;
		}
		return false;
		
	}
	
	public static void main(String[] args) {
		SelfCheckoutStation station = new SelfCheckoutStation();
		ElectronicScale scale = new ElectronicScale();
		BarcodeScanner scanner1 = new BarcodeScanner();
		Numeral[] code = {Numeral.one,Numeral.two,Numeral.three,Numeral.four};
 		Barcode barcode = new Barcode(code);
		long ms = 100;
		
		Mass mass = new Mass(ms);
		BarcodedItem barcodedItem = new BarcodedItem(barcode, mass);
		scanner1.scan(barcodedItem);
	}
	
	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}
	
	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
		// TODO Auto-generated method stub
		
	}
	
	
}