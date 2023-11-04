 package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.Session;

import powerutility.PowerGrid;

/* 
 * Software Testing for LThe Local Market Place Software
 * 
 * Firdovsi Aliyev 30178471
 * Jack Graver 10187274
 * Maheen Nizamani 30172615
 * Minori Olguin 30035923
 * Sarthak Monga 30190643
 * Tanjib Riasat 30170130
 * 
 * */


public class SoftwareTesting {
	
	Session session;
	ArrayList<BarcodedItem> orderItems;
	Numeral[] testCode = {Numeral.one,Numeral.two,Numeral.three,Numeral.four};
	Barcode testBarcode = new Barcode(testCode);
	SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation();
	double testWeight = 5.0;

	Mass testMass = new Mass(2L);
	double marginOfError = 0.1;

	
	@Before
	public void setup() {
		session = Session.getInstance();
		orderItems = new ArrayList<BarcodedItem>();
		selfCheckoutStation.plugIn(PowerGrid.instance());
		selfCheckoutStation.turnOn();
	
	}
	
	//Testing for Session Class
	
	//Tests if the session returns true for isActive() method after activate() method is called
	@Test
	public void sessionIsActiveTest() {
		session.activate();
		assertTrue(session.isActive());
	}
	
	//Tests if the session returns false for isActive() method after deactivate() method is called
	@Test
	public void sessionIsNotActiveTest() {
		session.deactivate();
		assertFalse(session.isActive());
	}
	
	//Test to see if a new item is added to orderItems when newOrderItem is called
	@Test
	public void newOrderedItemTest() {
		BarcodedItem testBarcodedItem = new BarcodedItem(testBarcode, new Mass(2L));
		session.newOrderItem(testBarcodedItem);
		orderItems.add(testBarcodedItem);
		assertEquals(orderItems,session.getOrderItem());
	}
	
	// Exception handling needed in code for 
	// adding a null item, should result in a null pointer exception
//	@Test(expected = NullPointerException.class)
//	public void newOrderedNullItemTest() {
//		BarcodedItem testBarcodedItem = null;
//		session.newOrderItem(testBarcodedItem);
//		orderItems.add(testBarcodedItem);
//		session.getOrderItem();
//	} 
	
	//Test to see if a addTotalExcpectedWeight updates when weight is added
	@Test
	public void addTotalExpectedWeightTest() {
		session.addTotalExpectedWeight(testWeight);
		assertEquals(testWeight, session.getTotalExpectedWeight(), marginOfError);
	}
	
	//Test to see if a addAmountDue updates when amount is added
	@Test
	public void addAmountDueTest() {
		double testAmount = session.getAmountDue() + 1;
		session.addAmountDue(1);
		assertEquals(testAmount, session.getAmountDue(), marginOfError);
	}
	
	//Test to see if a addAmountDue updates when amount is subtracted
	@Test
	public void subAmountDueTest() {
		session.addAmountDue(3);
		session.subAmountDue(2);
		assertEquals(1, session.getAmountDue(), marginOfError);
	}
	
	//Test to see if setWeightDiscrepancy returns correct boolean value true
	@Test
	public void setWeightDiscrepancyTest() {
		session.setWeightDiscrepancy();
		assertTrue(session.hasWeightDiscrepancy());
	}
	
	//Test to see if setNoWeightDiscrepancy returns correct boolean value false
	@Test
	public void setNoWeightDiscrepancyTest() {
		session.setNoWeightDiscrepancy();
		assertFalse(session.hasWeightDiscrepancy());
	}
}




