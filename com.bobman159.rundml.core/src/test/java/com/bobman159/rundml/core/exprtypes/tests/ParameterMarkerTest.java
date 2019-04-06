package com.bobman159.rundml.core.exprtypes.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.exprtypes.ParmMarker;

class ParameterMarkerTest {

	@BeforeAll
	static void setUpBeforeClass() {
		//no set up needed at this time
	}

	@AfterAll
	static void tearDownAfterClass() {
		//no tear down needed at this time
	}

	@BeforeEach
	void setUp()  {
		//no set up needed at time
	}

	@AfterEach
	void tearDown() {
		//no tear down needed at this time
	}

	/**
	 * Tests the JDBC type and value information for a ParmMarker
	 */
	@Test
	void testTypeValue() {
		
		int intTest = 10;
		ParmMarker marker = new ParmMarker(Types.INTEGER,intTest);
		String sqlGen = marker.serialize();
		Assertions.assertEquals("?",sqlGen);
		Assertions.assertEquals(Types.INTEGER, marker.getParmType());
		Assertions.assertEquals("10", marker.getValue());

		String strTest = "abc";
		ParmMarker marker2 = new ParmMarker(Types.VARCHAR,strTest);
		String sqlGen2 = marker2.serialize();
		Assertions.assertEquals("?",sqlGen2);
		Assertions.assertEquals(Types.VARCHAR, marker2.getParmType());
		Assertions.assertEquals("abc", marker2.getValue());
		
	}
	
	//TODO: Implement Bind tests for parameter markers
//	@Test
//	void testBind() {
//		fail("Not yet implemented");
//	}

}
