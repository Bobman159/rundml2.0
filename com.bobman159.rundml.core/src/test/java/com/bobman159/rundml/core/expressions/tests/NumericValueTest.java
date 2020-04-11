package com.bobman159.rundml.core.expressions.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;

class NumericValueTest {

	//TODO: Test math operations (add, subtract etc) number to other expression types (CASE, COLUMN, parm marker)
	
	private short shortTen = 10;
	private short twenty = 20;
	private double dblTen = 10.001;
	private double dblThirty = 30.225;
	
	private static final String TENTIMES20 = "10 * 20";
	private static final String TENPLUS20 = "10 + 20";
	private static final String TENDIV20 = "10 / 20";
	
	@BeforeAll
	static void setUpBeforeClass()  {
		//no set up needed at this time
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
	void addPrimitives() {

		String expr = Expression.number(10).add(20).serialize();
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = Expression.number(shortTen).add(twenty).serialize();
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = Expression.number(dblTen).add(20).add(dblThirty).serialize();
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = Expression.number(dblTen).add(20).add(dblThirty)
							.add(40).serialize();		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);

	}
	
	@Test
	void addExpressions() {

		String expr = Expression.number(10).add(Expression.number(20)).serialize();
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = Expression.number(shortTen).add(Expression.number(twenty))
								 .serialize();
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = Expression.number(dblTen)
								 .add(Expression.number(20))
								 .add(Expression.number(dblThirty)).serialize();
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = Expression.number(dblTen)
								 .add(Expression.number(20))
								 .add(Expression.number(dblThirty))
								 .add(Expression.number(40)).serialize();		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);
	}
	
	
	@Test
	void subtractPrimitives() {

		String expr = Expression.number(20).subtract(10).serialize();
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = Expression.number(shortTen).subtract(twenty).serialize();
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = Expression.number(dblTen).subtract(20).subtract(dblThirty)
								 .serialize();
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = Expression.number(dblTen).subtract(20).subtract(dblThirty)
							.subtract(40).serialize();		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
	}
	
	@Test
	void subtractExpressions() {
		
		String expr = Expression.number(20).subtract(Expression.number(10))
								.serialize();
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = Expression.number(shortTen)
								 .subtract(Expression.number(twenty)).serialize();
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = Expression.number(dblTen)
								 .subtract(Expression.number(20))
								 .subtract(Expression.number(dblThirty))
								 .serialize();
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = Expression.number(dblTen)
								 .subtract(Expression.number(20))
								 .subtract(Expression.number(dblThirty))
								 .subtract(Expression.number(40)).serialize();		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
		
	}

	@Test
	void multiplyPrimitives() {
		
		String expr = Expression.number(10).multiply(20).serialize();
		Assertions.assertEquals("10 * 20",expr);
		
		String expr2 = Expression.number(shortTen).multiply(twenty).serialize();
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = Expression.number(dblTen).multiply(20).multiply(dblThirty)
								 .serialize();
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = Expression.number(dblTen).multiply(20).multiply(dblThirty)
								 .multiply(40).serialize();		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);

	}
	
	@Test
	void multiplyExpressions() {
		
		String expr = Expression.number(20).multiply(Expression.number(10))
								.serialize();
		Assertions.assertEquals("20 * 10",expr);
		
		String expr2 = Expression.number(shortTen)
								 .multiply(Expression.number(twenty)).serialize();
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = Expression.number(dblTen)
								 .multiply(Expression.number(20))
								 .multiply(Expression.number(dblThirty))
								 .serialize();
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = Expression.number(dblTen)
								 .multiply(Expression.number(20))
								 .multiply(Expression.number(dblThirty))
								 .multiply(Expression.number(40)).serialize();		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);
		
	}
	
	@Test
	void dividePrimitives() {
		
		String expr = Expression.number(10).divide(20).serialize();
		Assertions.assertEquals(TENDIV20,expr);
		
		String expr2 = Expression.number(shortTen).divide(twenty).serialize();
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = Expression.number(dblTen).divide(20).divide(dblThirty)
								 .serialize();
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = Expression.number(dblTen).divide(20).divide(dblThirty)
								 .divide(40).serialize();		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);

	}
	
	@Test
	void divideExpressions() {
		
		String expr = Expression.number(20).divide(Expression.number(10))
								.serialize();
		Assertions.assertEquals("20 / 10",expr);
		
		String expr2 = Expression.number(shortTen)
								 .divide(Expression.number(twenty)).serialize();
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = Expression.number(dblTen)
								 .divide(Expression.number(20))
								 .divide(Expression.number(dblThirty))
								 .serialize();
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = Expression.number(dblTen)
								 .divide(Expression.number(20))
								 .divide(Expression.number(dblThirty))
								 .divide(Expression.number(40)).serialize();		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);
		
	}



}
