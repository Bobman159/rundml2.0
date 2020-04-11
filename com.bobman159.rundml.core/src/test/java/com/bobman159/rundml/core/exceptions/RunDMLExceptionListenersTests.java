package com.bobman159.rundml.core.exceptions;

import java.beans.ExceptionListener;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RunDMLExceptionListenersTests {

	boolean listenerCalled = false;
	boolean msgisCorrect = false;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		RunDMLExceptionListeners exListeners = RunDMLExceptionListeners.getInstance();
		exListeners.clearListeners();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetInstance() {
		RunDMLExceptionListeners exListener = RunDMLExceptionListeners.getInstance();
		Assertions.assertNotNull(exListener);
	}

	@Test
	void testAddListener() {
	
		
		RunDMLExceptionListeners exListener = RunDMLExceptionListeners.getInstance();
		exListener.addListener(new ExceptionListener() {			
			
			@Override
			public void exceptionThrown(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		Assertions.assertEquals(1, exListener.getListenersSize());

	}

	@Test
	void testRemoveListener() {
		
		RunDMLExceptionListeners exListener = RunDMLExceptionListeners.getInstance();
		ExceptionListener rdexListener1 = new RunDMLExceptionListener();
		exListener.addListener(rdexListener1); 
		ExceptionListener rdexListener2 = new RunDMLExceptionListener();
		exListener.addListener(rdexListener2); 
		ExceptionListener rdexListener3 = new RunDMLExceptionListener();
		exListener.addListener(rdexListener3); 		
		Assertions.assertEquals(3, exListener.getListenersSize());
		
		exListener.removeListener(rdexListener2);
		Assertions.assertEquals(2, exListener.getListenersSize());
		exListener.removeListener(rdexListener1);
		Assertions.assertEquals(1, exListener.getListenersSize());
		exListener.removeListener(rdexListener3);
		Assertions.assertEquals(0, exListener.getListenersSize());
	}

	@Test
	void testNotifyListenersSQLException() {
		
		listenerCalled = false;
		msgisCorrect = false;
		
		RunDMLExceptionListeners exListeners = RunDMLExceptionListeners.getInstance();
		final String TEST_EX_MSG = "Test SQL Exception Message";
		SQLException sqlEx = new SQLException(TEST_EX_MSG);
				
		ExceptionListener exListener = new ExceptionListener() {
			@Override
			public void exceptionThrown(Exception e) {
				listenerCalled = true;
				if (e.getMessage().equals(TEST_EX_MSG)) {
					msgisCorrect = true;
				}
			}
			
		};
		exListeners.addListener(exListener);
		exListeners.notifyListeners(sqlEx);
		
		Assertions.assertTrue(listenerCalled);
		Assertions.assertTrue(msgisCorrect);

	}

	@Test
	void testNotifyListenersException() {
		
		listenerCalled = false;
		msgisCorrect = false;
		
		RunDMLExceptionListeners exListeners = RunDMLExceptionListeners.getInstance();
		final String TEST_EX_MSG = "Test Exception Message";
		Exception ex = new Exception(TEST_EX_MSG);
				
		ExceptionListener exListener = new ExceptionListener() {
			@Override
			public void exceptionThrown(Exception e) {
				listenerCalled = true;
				if (e.getMessage().equals(TEST_EX_MSG)) {
					msgisCorrect = true;
				}
			}
			
		};
		exListeners.addListener(exListener);
		exListeners.notifyListeners(ex);
		
		Assertions.assertTrue(listenerCalled);
		Assertions.assertTrue(msgisCorrect);

	}
	


}
