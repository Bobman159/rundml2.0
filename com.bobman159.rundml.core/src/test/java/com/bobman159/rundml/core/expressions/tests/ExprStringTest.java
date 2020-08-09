package com.bobman159.rundml.core.expressions.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

class ExprStringTest {

	private TestBaseSQLSerializer serializer = new TestBaseSQLSerializer();
	
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
		IExpressionNode  strExpr = SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("abc"))
																		 .concat("def");
		String expr = serializer.serialize(strExpr);
		Assertions.assertEquals("\'abc\' || \'def\'",expr);

		Column lhs = new Column("ingredient");
		Column rhs = new Column("unit_of_measure");
		
		IExpressionNode strExpr2 = SQLTypeFactory.getInstance().stringExpression(lhs).concat(rhs);
		String expr2 = serializer.serialize(strExpr2);
		Assertions.assertEquals("ingredient || unit_of_measure",expr2);
		
		IExpressionNode strExpr3 = SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("Ingred: ")).concat(lhs)				
								 										 .concat(SQLTypeFactory.getInstance().constant("UnitMeasure: "))
								 										 .concat(rhs);
		String expr3 = serializer.serialize(strExpr3);
		Assertions.assertEquals("\'Ingred: \' || ingredient || \'UnitMeasure: \' || unit_of_measure",expr3);

		IExpressionNode strExpr4 = SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().column("ingredient"))
								 .concat(SQLTypeFactory.getInstance().column("unit_of_measure"));
		String expr4 = serializer.serialize(strExpr4);
		Assertions.assertEquals("ingredient || unit_of_measure",expr4);
		
		IExpressionNode strExpr5 = SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().parm(Types.VARCHAR, "varchar_value"))
				 														 .concat(SQLTypeFactory.getInstance().parm(Types.CHAR, "char_value"));
		String expr5 = serializer.serialize(strExpr5);
		Assertions.assertEquals("? || ?",expr5);
		
	}

}
