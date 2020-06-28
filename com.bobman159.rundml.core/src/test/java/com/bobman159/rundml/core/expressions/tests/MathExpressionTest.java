package com.bobman159.rundml.core.expressions.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.factory.RunDMLTestFactory;
import com.bobman159.rundml.core.sql.impl.SQLClauseClient;

class MathExpressionTest {

	RunDMLTestFactory testFactory = RunDMLTestFactory.getInstance();
	//TODO: Test math operations (add, subtract etc) number to other expression types (CASE, COLUMN, parm marker)
	
	private short shortTen = 10;
	private short twenty = 20;
	private double dblTen = 10.001;
	private double dblThirty = 30.225;
	
	private static final String TENTIMES20 = "10 * 20";
	private static final String TENPLUS20 = "10 + 20";
	private static final String TENDIV20 = "10 / 20";
	
	private static final SQLClauseClient client = SQLClauseClient.getInstance();
	
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

		String expr = client.toSQLClause(testFactory.mathExpression(10).add(20));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen).add(twenty));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen).add(20).add(dblThirty));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen).add(20).add(dblThirty).add(40));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);

	}
	
	@Test
	void addExpressions() {

		String expr = client.toSQLClause(testFactory.mathExpression(10).add(testFactory.constant(20)));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen).add(testFactory.constant(twenty)));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .add(testFactory.constant(20))
								 .add(testFactory.constant(dblThirty)));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .add(testFactory.constant(20))
								 .add(testFactory.constant(dblThirty))
								 .add(testFactory.constant(40)));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);
	}
	
	
	@Test
	void subtractPrimitives() {

		String expr = client.toSQLClause(testFactory.mathExpression(20).subtract(10));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen).subtract(twenty));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen).subtract(20).subtract(dblThirty));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen).subtract(20).subtract(dblThirty)
							.subtract(40));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
	}
	
	@Test
	void subtractExpressions() {
		
		String expr = client.toSQLClause(testFactory.mathExpression(20).subtract(testFactory.constant(10)));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen)
								 .subtract(testFactory.constant(twenty)));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .subtract(testFactory.constant(20))
								 .subtract(testFactory.constant(dblThirty)));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .subtract(testFactory.constant(20))
								 .subtract(testFactory.constant(dblThirty))
								 .subtract(testFactory.constant(40)));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
		
	}

	@Test
	void multiplyPrimitives() {
		
		String expr = client.toSQLClause(testFactory.mathExpression(10).multiply(20));
		Assertions.assertEquals("10 * 20",expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen).multiply(twenty));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen).multiply(20).multiply(dblThirty));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen).multiply(20).multiply(dblThirty)
								 .multiply(40));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);

	}
	
	@Test
	void multiplyExpressions() {
		
		String expr = client.toSQLClause(testFactory.mathExpression(20).multiply(testFactory.constant(10)));
		Assertions.assertEquals("20 * 10",expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen)
								 .multiply(testFactory.constant(twenty)));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .multiply(testFactory.constant(20))
								 .multiply(testFactory.constant(dblThirty)));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .multiply(testFactory.constant(20))
								 .multiply(testFactory.constant(dblThirty))
								 .multiply(testFactory.constant(40)));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);
		
	}
	
	@Test
	void dividePrimitives() {
		
		String expr = client.toSQLClause(testFactory.mathExpression(10).divide(20));
		Assertions.assertEquals(TENDIV20,expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen).divide(twenty));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen).divide(20).divide(dblThirty));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen).divide(20).divide(dblThirty)
								 .divide(40));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);

	}
	
	@Test
	void divideExpressions() {
		
		String expr = client.toSQLClause(testFactory.mathExpression(20).divide(testFactory.constant(10)));
		Assertions.assertEquals("20 / 10",expr);
		
		String expr2 = client.toSQLClause(testFactory.mathExpression(shortTen)
								 .divide(testFactory.constant(twenty)));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .divide(testFactory.constant(20))
								 .divide(testFactory.constant(dblThirty)));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = client.toSQLClause(testFactory.mathExpression(dblTen)
								 .divide(testFactory.constant(20))
								 .divide(testFactory.constant(dblThirty))
								 .divide(testFactory.constant(40)));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);
		
	}



}
