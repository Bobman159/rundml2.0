package com.bobman159.rundml.core.expressions.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

class MathExpressionTest {

	//TODO: Test math operations (add, subtract etc) number to other expression types (CASE, COLUMN, parm marker)
	
	private short shortTen = 10;
	private short twenty = 20;
	private double dblTen = 10.001;
	private double dblThirty = 30.225;
	
	private static final String TENTIMES20 = "10 * 20";
	private static final String TENPLUS20 = "10 + 20";
	private static final String TENDIV20 = "10 / 20";
	
	private static final TestBaseSQLSerializer serializer = new TestBaseSQLSerializer();
	
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

		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(10).add(20));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen).add(twenty));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).add(20).add(dblThirty));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).add(20).add(dblThirty).add(40));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);

	}
	
	@Test
	void addExpressions() {

		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(10).add(SQLTypeFactory.getInstance().constant(20)));
		Assertions.assertEquals(TENPLUS20,expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen).add(SQLTypeFactory.getInstance().constant(twenty)));
		Assertions.assertEquals(TENPLUS20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .add(SQLTypeFactory.getInstance().constant(20))
								 .add(SQLTypeFactory.getInstance().constant(dblThirty)));
		Assertions.assertEquals("10.001 + 20 + 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .add(SQLTypeFactory.getInstance().constant(20))
								 .add(SQLTypeFactory.getInstance().constant(dblThirty))
								 .add(SQLTypeFactory.getInstance().constant(40)));		
		Assertions.assertEquals("10.001 + 20 + 30.225 + 40",expr4);
	}
	
	
	@Test
	void subtractPrimitives() {

		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(20).subtract(10));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen).subtract(twenty));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).subtract(20).subtract(dblThirty));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).subtract(20).subtract(dblThirty)
							.subtract(40));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
	}
	
	@Test
	void subtractExpressions() {
		
		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(20).subtract(SQLTypeFactory.getInstance().constant(10)));
		Assertions.assertEquals("20 - 10",expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen)
								 .subtract(SQLTypeFactory.getInstance().constant(twenty)));
		Assertions.assertEquals("10 - 20",expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .subtract(SQLTypeFactory.getInstance().constant(20))
								 .subtract(SQLTypeFactory.getInstance().constant(dblThirty)));
		Assertions.assertEquals("10.001 - 20 - 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .subtract(SQLTypeFactory.getInstance().constant(20))
								 .subtract(SQLTypeFactory.getInstance().constant(dblThirty))
								 .subtract(SQLTypeFactory.getInstance().constant(40)));		
		Assertions.assertEquals("10.001 - 20 - 30.225 - 40",expr4);
		
	}

	@Test
	void multiplyPrimitives() {
		
		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(10).multiply(20));
		Assertions.assertEquals("10 * 20",expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen).multiply(twenty));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).multiply(20).multiply(dblThirty));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).multiply(20).multiply(dblThirty)
								 .multiply(40));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);

	}
	
	@Test
	void multiplyExpressions() {
		
		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(20).multiply(SQLTypeFactory.getInstance().constant(10)));
		Assertions.assertEquals("20 * 10",expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen)
								 .multiply(SQLTypeFactory.getInstance().constant(twenty)));
		Assertions.assertEquals(TENTIMES20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .multiply(SQLTypeFactory.getInstance().constant(20))
								 .multiply(SQLTypeFactory.getInstance().constant(dblThirty)));
		Assertions.assertEquals("10.001 * 20 * 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .multiply(SQLTypeFactory.getInstance().constant(20))
								 .multiply(SQLTypeFactory.getInstance().constant(dblThirty))
								 .multiply(SQLTypeFactory.getInstance().constant(40)));		
		Assertions.assertEquals("10.001 * 20 * 30.225 * 40",expr4);
		
	}
	
	@Test
	void dividePrimitives() {
		
		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(10).divide(20));
		Assertions.assertEquals(TENDIV20,expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen).divide(twenty));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).divide(20).divide(dblThirty));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen).divide(20).divide(dblThirty)
								 .divide(40));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);

	}
	
	@Test
	void divideExpressions() {
		
		String expr = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(20).divide(SQLTypeFactory.getInstance().constant(10)));
		Assertions.assertEquals("20 / 10",expr);
		
		String expr2 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(shortTen)
								 .divide(SQLTypeFactory.getInstance().constant(twenty)));
		Assertions.assertEquals(TENDIV20,expr2);
		
		String expr3 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .divide(SQLTypeFactory.getInstance().constant(20))
								 .divide(SQLTypeFactory.getInstance().constant(dblThirty)));
		Assertions.assertEquals("10.001 / 20 / 30.225",expr3);
		
		String expr4 = serializer.serialize(SQLTypeFactory.getInstance().mathExpression(dblTen)
								 .divide(SQLTypeFactory.getInstance().constant(20))
								 .divide(SQLTypeFactory.getInstance().constant(dblThirty))
								 .divide(SQLTypeFactory.getInstance().constant(40)));		
		Assertions.assertEquals("10.001 / 20 / 30.225 / 40",expr4);
		
	}



}
