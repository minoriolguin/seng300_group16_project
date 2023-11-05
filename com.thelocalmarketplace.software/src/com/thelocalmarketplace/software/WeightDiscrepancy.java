package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import java.util.Scanner;
/*
 * 
 * Firdovsi Aliyev 30178471
 * Jack Graver 10187274
 * Maheen Nizamani 30172615
 * Minori Olguin 30035923
 * Sarthak Monga 30190643
 * Tanjib Riasat 30170130
 * 
 * */

public class WeightDiscrepancy {

	public String WeightDiscrepancyMessage(SelfCheckoutStation selfCheckoutStation, BarcodedProduct product) {
		String choice="NO";
		String finChoice = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println(product.getDescription() + " was not added to bagging area");
		while(!choice.equals("YES")) {
			System.out.println("There is a weight discrepancy, as you scanned an item and didn't put it in the bagging area \n"
					+ "Place item in the bagging Area (Yes/No): ");
			choice = scanner.nextLine().toUpperCase();
			if(choice.equals("NO")) { //No means that a customer is not willing to put the item in the bagging are (he might have changed his decision)
				System.out.println("Do you want to cancel this Item? (Yes/No) [If yes, then the station will continue as usual, else you have to put it in the bagging area]");
				finChoice = scanner.nextLine().toUpperCase();
				System.out.println("finChoice: " + finChoice);
				if(finChoice.equals("YES")){
					return "Cancel";
				}
				else {
					continue;
				}
			} else {
				return "Add";
			}
			
		}
		return "Cancel";
	}
	
}

