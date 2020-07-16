package com.bobman159.rundml.core.sql.types.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.impl.Table;

class TableTest {

	private final TestBaseSQLSerializer serializer = new TestBaseSQLSerializer();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void tableTest() {
		
		//un qualified table test
		Table unqualified = new Table("runDMLTable");
		Assertions.assertEquals(SQLType.TABLE, unqualified.getType());
		Assertions.assertEquals("runDMLTable", serializer.serialize(unqualified));

		//qualified table test
		Table qualified = new Table("runDMLSchema","runDMLTable");
		Assertions.assertEquals(SQLType.TABLE, qualified.getType());
		Assertions.assertEquals("runDMLSchema.runDMLTable", serializer.serialize(qualified));
	}

}
