package com.bobman159.rundml.core.expressions.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.sql.impl.SQLClauseClient;

class MathExpressionTest {

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

		String expr = client.toSQLClause(Expression.mathExpression(10).add(20));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen).add(twenty));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen).add(20).add(dblThirty));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen).add(20).add(dblThirty).add(40));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);

	}
	
	@Test
	void addExpressions() {

		String expr = client.toSQLClause(Expression.mathExpression(10).add(Expression.constant(20)));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen).add(Expression.constant(twenty)));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .add(Expression.constant(20))
								 .add(Expression.constant(dblThirty)));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .add(Expression.constant(20))
								 .add(Expression.constant(dblThirty))
								 .add(Expression.constant(40)));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);
	}
	
	
	@Test
	void subtractPrimitives() {

		String expr = client.toSQLClause(Expression.mathExpression(20).subtract(10));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen).subtract(twenty));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen).subtract(20).subtract(dblThirty));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen).subtract(20).subtract(dblThirty)
							.subtract(40));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
	}
	
	@Test
	void subtractExpressions() {
		
		String expr = client.toSQLClause(Expression.mathExpression(20).subtract(Expression.constant(10)));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen)
								 .subtract(Expression.constant(twenty)));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .subtract(Expression.constant(20))
								 .subtract(Expression.constant(dblThirty)));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .subtract(Expression.constant(20))
								 .subtract(Expression.constant(dblThirty))
								 .subtract(Expression.constant(40)));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
		
	}

	@Test
	void multiplyPrimitives() {
		
		String expr = client.toSQLClause(Expression.mathExpression(10).multiply(20));
		Assertions.assertEquals("10 * 20",expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen).multiply(twenty));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen).multiply(20).multiply(dblThirty));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen).multiply(20).multiply(dblThirty)
								 .multiply(40));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);

	}
	
	@Test
	void multiplyExpressions() {
		
		String expr = client.toSQLClause(Expression.mathExpression(20).multiply(Expression.constant(10)));
		Assertions.assertEquals("20 * 10",expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen)
								 .multiply(Expression.constant(twenty)));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .multiply(Expression.constant(20))
								 .multiply(Expression.constant(dblThirty)));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .multiply(Expression.constant(20))
								 .multiply(Expression.constant(dblThirty))
								 .multiply(Expression.constant(40)));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);
		
	}
	
	@Test
	void dividePrimitives() {
		
		String expr = client.toSQLClause(Expression.mathExpression(10).divide(20));
		Assertions.assertEquals(TENDIV20,expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen).divide(twenty));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen).divide(20).divide(dblThirty));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen).divide(20).divide(dblThirty)
								 .divide(40));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);

	}
	
	@Test
	void divideExpressions() {
		
		String expr = client.toSQLClause(Expression.mathExpression(20).divide(Expression.constant(10)));
		Assertions.assertEquals("20 / 10",expr);
		
		String expr2 = client.toSQLClause(Expression.mathExpression(shortTen)
								 .divide(Expression.constant(twenty)));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .divide(Expression.constant(20))
								 .divide(Expression.constant(dblThirty)));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = client.toSQLClause(Expression.mathExpression(dblTen)
								 .divide(Expression.constant(20))
								 .divide(Expression.constant(dblThirty))
								 .divide(Expression.constant(40)));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);
		
	}



}
