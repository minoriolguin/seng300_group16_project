package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;

import java.math.BigDecimal;
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

	
	private BarcodedProduct product;
	private BigDecimal weight;
	
	public WeightDiscrepancy (BarcodedProduct product, BigDecimal weight) {
		this.product = product;
		this.weight = weight;
	}
	
	public BarcodedProduct getProduct() {
		return product;
	}

	public BigDecimal getWeight() {
		return weight;
	}
}
	

