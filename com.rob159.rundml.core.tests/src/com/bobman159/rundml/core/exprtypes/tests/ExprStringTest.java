package com.bobman159.rundml.core.exprtypes.tests;

import java.sql.Types;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.exprtypes.Column;

class ExprStringTest {

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
	void testConncat() {
		String expr = Expression.string("abc").concat("def").serialize();
		Assert.assertEquals("\'abc\' || \'def\'",expr);

		Column lhs = new Column("ingredient",Types.CHAR);
		Column rhs = new Column("unit_of_measure",Types.VARCHAR);
		
		String expr2 = lhs.concat(rhs).serialize();
		Assert.assertEquals("INGREDIENT || UNIT_OF_MEASURE",expr2);
		
		String expr3 = Expression.string("Ingred: ").concat(lhs)				
								 .concat(Expression.string("UnitMeasure: "))
								 .concat(rhs).serialize();
		Assert.assertEquals("\'Ingred: \' || INGREDIENT || \'UnitMeasure: \' || UNIT_OF_MEASURE",expr3);

	}

}
