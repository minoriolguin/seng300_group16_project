package com.thelocalmarketplace.software.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SessionSimulation;
import com.thelocalmarketplace.software.StartSession;

public class SoftwareTesting {
	
	boolean sessionStart = false;
	SessionSimulation session = new SessionSimulation();
	StartSession sessionStarted = new StartSession(sessionStart);
	
	@Before
	public void setup() {
	
	}
	
	@Test
	public void sessionStartedTest() {
		sessionStarted.setSessionStarted(true);
		
	}
}
