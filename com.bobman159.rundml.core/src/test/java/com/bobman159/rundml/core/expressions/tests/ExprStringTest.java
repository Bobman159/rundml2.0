package com.bobman159.rundml.core.expressions.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.factory.RunDMLTestFactory;
import com.bobman159.rundml.core.sql.impl.SQLClauseClient;
import com.bobman159.rundml.core.sql.types.impl.Column;

class ExprStringTest {
	
	private final RunDMLTestFactory testFactory = RunDMLTestFactory.getInstance();

	@BeforeAll
	static void setUpBeforeClass() {
		//No Set up needed at this time
	}

	@AfterAll
	static void tearDownAfterClass() {
		//No tear down needed at this time
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
	void testConncat() {
		IExpressionNode  strExpr = testFactory.stringExpression(testFactory.constant("abc"))
																		 .concat("def");
		String expr = SQLClauseClient.getInstance().toSQLClause(strExpr);
		Assertions.assertEquals("\'abc\' || \'def\'",expr);

		Column lhs = new Column("ingredient");
		Column rhs = new Column("unit_of_measure");
		
		IExpressionNode strExpr2 = testFactory.stringExpression(lhs).concat(rhs);
		String expr2 = SQLClauseClient.getInstance().toSQLClause(strExpr2);
		Assertions.assertEquals("ingredient || unit_of_measure",expr2);
		
		IExpressionNode strExpr3 = testFactory.stringExpression(testFactory.constant("Ingred: ")).concat(lhs)				
								 										 .concat(testFactory.constant("UnitMeasure: "))
								 										 .concat(rhs);
		String expr3 = SQLClauseClient.getInstance().toSQLClause(strExpr3);
		Assertions.assertEquals("\'Ingred: \' || ingredient || \'UnitMeasure: \' || unit_of_measure",expr3);

		IExpressionNode strExpr4 = testFactory.stringExpression(testFactory.column("ingredient"))
								 .concat(testFactory.column("unit_of_measure"));
		String expr4 = SQLClauseClient.getInstance().toSQLClause(strExpr4);
		Assertions.assertEquals("ingredient || unit_of_measure",expr4);
		
		IExpressionNode strExpr5 = testFactory.stringExpression(testFactory.parm(Types.VARCHAR, "varchar_value"))
				 														 .concat(testFactory.parm(Types.CHAR, "char_value"));
		String expr5 = SQLClauseClient.getInstance().toSQLClause(strExpr5);
		Assertions.assertEquals("? || ?",expr5);
		
	}

}
