package com.bobman159.rundml.core.tabledef.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.tabledef.TableDefinition;

class TableDefinitionTests {
	
	private static TableDefinition tbDef;
	private static final String DFLTINTEGER = "dfltInteger";

	@BeforeAll
	static void setUpBeforeClass() {
		tbDef = new TableDefinition("rundml","typetest");
	}

	@AfterAll
	static void tearDownAfterClass() {
		//no tear down needed at this time
	}

	@BeforeEach
	void setUp() {
		//no set up needed at this time
	}

	@AfterEach
	void tearDown() {
		//no tear down needed at this time
	}

	@Test
	void testAddColumn() {
		tbDef.addColumn(DFLTINTEGER, Types.INTEGER);
		tbDef.addColumn("dfltNumber72", Types.DECIMAL);
		tbDef.addColumn("notNullChar", Types.CHAR);
		
		Assertions.assertEquals(3,tbDef.columns().count());
		
		Column col01 = tbDef.column(DFLTINTEGER);
		Assertions.assertEquals("DFLTINTEGER", col01.getName());
		Assertions.assertEquals(Types.INTEGER, col01.getType());
		
		Column col02 = tbDef.column("dfltNumber72");
		Assertions.assertEquals("DFLTNUMBER72", col02.getName());
		Assertions.assertEquals(Types.DECIMAL, col02.getType());
		
		Column col03 = tbDef.column("notNullChar");
		Assertions.assertEquals("NOTNULLCHAR", col03.getName());
		Assertions.assertEquals(Types.CHAR, col03.getType());
		
		
		tbDef.columns().forEach(col -> {

			if (col.getName().equalsIgnoreCase(DFLTINTEGER)) {
				Assertions.assertEquals("DFLTINTEGER",col.getName());
				Assertions.assertEquals(Types.INTEGER,col.getType());				
			} else if (col.getName().equalsIgnoreCase("dfltnumber72")) {
				Assertions.assertEquals("DFLTNUMBER72",col.getName());
				Assertions.assertEquals(Types.DECIMAL,col.getType());				
			} else if (col.getName().equalsIgnoreCase("notnullchar")) {
				Assertions.assertEquals("NOTNULLCHAR",col.getName());
				Assertions.assertEquals(Types.CHAR,col.getType());
			} else {
				Assertions.fail("Column: " + col.getName() + 
					 " was not found in table definition");
			}
		});

	}

	@Test
	void testQualifedTableName() {
		String qualTbName = tbDef.qualifedTableName();
		Assertions.assertEquals("rundml.typetest", qualTbName);
	}

}
