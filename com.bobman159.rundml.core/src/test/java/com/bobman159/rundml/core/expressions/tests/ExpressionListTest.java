package com.bobman159.rundml.core.expressions.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.ExpressionList;
import com.bobman159.rundml.core.sql.BaseSQLSerializer;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;

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
		
		ISQLType [] exprArray = {new Column("column_name"),
										new ParmMarker(Types.SMALLINT,new NumericType(10)),
										SQLTypeFactory.mathExpression(SQLTypeFactory.constant(100)).divide(10),
										SQLTypeFactory.stringExpression(SQLTypeFactory.constant("ABC")).concat("DEF")
		};
											
		ExpressionList exprList = new ExpressionList();
		exprList.addExpressions(exprArray);
		String csv = new BaseSQLSerializer().serialize(exprList);
		Assertions.assertEquals("column_name,?,100 / 10,'ABC' || 'DEF'",csv);

	}
	
	@Test
	void addExpressionTest() {
						
		ExpressionList exprList = new ExpressionList();
		
		exprList.addExpression(new Column("column_name"));
		exprList.addExpression(new ParmMarker(Types.SMALLINT,new NumericType(10)));
		exprList.addExpression(SQLTypeFactory.mathExpression(SQLTypeFactory.constant(100)).divide(10));
		exprList.addExpression(SQLTypeFactory.stringExpression(SQLTypeFactory.constant("ABC")).concat("DEF"));
		String csv = new BaseSQLSerializer().serialize(exprList);
		Assertions.assertEquals("column_name,?,100 / 10,'ABC' || 'DEF'",csv);		

	}
	
}
