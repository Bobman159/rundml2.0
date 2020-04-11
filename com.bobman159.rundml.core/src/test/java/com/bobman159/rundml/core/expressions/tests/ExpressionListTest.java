package com.bobman159.rundml.core.expressions.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.expressions.ExpressionList;
import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.exprtypes.NumericValue;
import com.bobman159.rundml.core.exprtypes.ParmMarker;

class ExpressionListTest {

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
	void addExpressionArrayTest() {
		
		IExpression [] exprArray = {new Column("column_name"),
									new ParmMarker(Types.SMALLINT,new NumericValue(10)),
									Expression.number(100).divide(10),
									Expression.string("ABC").concat("DEF")
		};
											
		ExpressionList exprList = new ExpressionList();
		exprList.addExpressions(exprArray);
		String csv = exprList.toCSV();
		Assertions.assertEquals(csv, "column_name,?,100 / 10,'ABC' || 'DEF'");

	}
	
	@Test
	void addExpressionTest() {
						
		ExpressionList exprList = new ExpressionList();
		
		exprList.addExpression(new Column("column_name"));
		exprList.addExpression(new ParmMarker(Types.SMALLINT,new NumericValue(10)));
		exprList.addExpression(Expression.number(100).divide(10));
		exprList.addExpression(Expression.string("ABC").concat("DEF"));
		String csv = exprList.toCSV();
		Assertions.assertEquals(csv, "column_name,?,100 / 10,'ABC' || 'DEF'");		

	}
	
}
