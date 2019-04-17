package com.bobman159.rundml.core.tabledef.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;

class ColumnTest {

	private static final String COLLHS = "COLLHS";
	private static final String COLRHS = "COLRHS";
	
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
	void testAdd() {
		
		Column colLhs = new Column(COLLHS,Types.INTEGER);
		String expr = colLhs.add(10).serialize();
		Assertions.assertEquals("COLLHS + 10",expr);
		
		Column colLhs2 = new Column(COLLHS,Types.INTEGER);
		String expr2 = colLhs2.add(10).serialize();
		Assertions.assertEquals("COLLHS + 10",expr2);
		
		Column colRhs = new Column(COLRHS,Types.SMALLINT);
		String expr3 = colRhs.add(10).serialize();
		Assertions.assertEquals("COLRHS + 10",expr3);
		
		String expr4 = Expression.number(10).add(colRhs).serialize();
		Assertions.assertEquals("10 + COLRHS",expr4);
		
		String expr5 = colLhs.add(colRhs).serialize();
		Assertions.assertEquals("COLLHS + COLRHS",expr5);
		
		String expr6 = colRhs.add(colLhs).serialize();
		Assertions.assertEquals("COLRHS + COLLHS",expr6);
	}
	
	@Test
	void testSubtract() {
		
		Column colLhs = new Column(COLLHS,Types.INTEGER);
		String expr = colLhs.subtract(10).serialize();
		Assertions.assertEquals("COLLHS - 10",expr);
		
		Column colLhs2 = new Column(COLLHS,Types.INTEGER);
		String expr2 = colLhs2.subtract(10).serialize();
		Assertions.assertEquals("COLLHS - 10",expr2);
		
		Column colRhs = new Column(COLRHS,Types.SMALLINT);
		String expr3 = colRhs.subtract(10).serialize();
		Assertions.assertEquals("COLRHS - 10",expr3);
		
		String expr4 = Expression.number(10).subtract(colRhs).serialize();
		Assertions.assertEquals("10 - COLRHS",expr4);
		
		String expr5 = colLhs.subtract(colRhs).serialize();
		Assertions.assertEquals("COLLHS - COLRHS",expr5);
		
		String expr6 = colRhs.subtract(colLhs).serialize();
		Assertions.assertEquals("COLRHS - COLLHS",expr6);
	}
	
	@Test
	void testMultiply() {
		
		Column colLhs = new Column(COLLHS,Types.INTEGER);
		String expr = colLhs.multiply(10).serialize();
		Assertions.assertEquals("COLLHS * 10",expr);
		
		Column colLhs2 = new Column(COLLHS,Types.INTEGER);
		String expr2 = colLhs2.multiply(10).serialize();
		Assertions.assertEquals("COLLHS * 10",expr2);
		
		Column colRhs = new Column(COLRHS,Types.SMALLINT);
		String expr3 = colRhs.multiply(10).serialize();
		Assertions.assertEquals("COLRHS * 10",expr3);
		
		String expr4 = Expression.number(10).multiply(colRhs).serialize();
		Assertions.assertEquals("10 * COLRHS",expr4);
		
		String expr5 = colLhs.multiply(colRhs).serialize();
		Assertions.assertEquals("COLLHS * COLRHS",expr5);
		
		String expr6 = colRhs.multiply(colLhs).serialize();
		Assertions.assertEquals("COLRHS * COLLHS",expr6);
	}
	
	@Test
	void testDivide() {
		
		Column colLhs = new Column(COLLHS,Types.INTEGER);
		String expr = colLhs.divide(10).serialize();
		Assertions.assertEquals("COLLHS / 10",expr);
		
		Column colLhs2 = new Column(COLLHS,Types.INTEGER);
		String expr2 = colLhs2.divide(10).serialize();
		Assertions.assertEquals("COLLHS / 10",expr2);
		
		Column colRhs = new Column(COLRHS,Types.SMALLINT);
		String expr3 = colRhs.divide(10).serialize();
		Assertions.assertEquals("COLRHS / 10",expr3);
		
		String expr4 = Expression.number(10).divide(colRhs).serialize();
		Assertions.assertEquals("10 / COLRHS",expr4);
		
		String expr5 = colLhs.divide(colRhs).serialize();
		Assertions.assertEquals("COLLHS / COLRHS",expr5);
		
		String expr6 = colRhs.divide(colLhs).serialize();
		Assertions.assertEquals("COLRHS / COLLHS",expr6);
	}
	
	@Test
	void testColumn() {
		
		Column colLhs = new Column(COLLHS,Types.INTEGER);
		Assertions.assertEquals(COLLHS,colLhs.getName());
		Assertions.assertEquals(Types.INTEGER,colLhs.getType());
	
	}

}
