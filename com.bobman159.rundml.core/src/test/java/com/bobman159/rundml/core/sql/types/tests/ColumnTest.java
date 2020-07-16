package com.bobman159.rundml.core.sql.types.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;

class ColumnTest {

	private static final String COLLHS = "COLLHS";
	
	@BeforeAll
	static void setUpBeforeClass() {
		//No set up needed
	}

	@AfterAll
	static void tearDownAfterClass() {
		//no tear down needed
	}

	@BeforeEach
	void setUp() {
		//no set up needed
	}

	@AfterEach
	void tearDown() {
		//no tear down needed
	}

	@Test
	void testColumn() {
		
		Column colLhs = new Column(COLLHS);
		Assertions.assertEquals(SQLType.COLUMN,colLhs.getType()); 
		Assertions.assertEquals(COLLHS,new TestBaseSQLSerializer().serialize(colLhs));

	}

}
