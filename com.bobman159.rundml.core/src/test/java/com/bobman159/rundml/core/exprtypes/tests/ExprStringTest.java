package com.bobman159.rundml.core.exprtypes.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;

class ExprStringTest {

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
		String expr = Expression.string("abc").concat("def").serialize();
		Assertions.assertEquals("\'abc\' || \'def\'",expr);

		Column lhs = new Column("ingredient");
		Column rhs = new Column("unit_of_measure");
		
		String expr2 = lhs.concat(rhs).serialize();
		Assertions.assertEquals("INGREDIENT || UNIT_OF_MEASURE",expr2);
		
		String expr3 = Expression.string("Ingred: ").concat(lhs)				
								 .concat(Expression.string("UnitMeasure: "))
								 .concat(rhs).serialize();
		Assertions.assertEquals("\'Ingred: \' || INGREDIENT || \'UnitMeasure: \' || UNIT_OF_MEASURE",expr3);

	}

}
