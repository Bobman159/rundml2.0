package com.bobman159.rundml.core.expressions.tests;

import java.sql.Types;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.impl.ExpressionList;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;
import com.bobman159.rundml.core.sql.serialize.impl.TestBaseSQLSerializer;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

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
										SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(100)).divide(10),
										SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("ABC")).concat("DEF")
		};
											
		ExpressionList exprList = (ExpressionList) CoreModelFactory.getInstance().createExpressionList();
		exprList.addExpressions(exprArray);
		String csv = new TestBaseSQLSerializer().serializeSQLTypeList(exprList.getExpressions());
		Assertions.assertEquals("column_name,?,100 / 10,'ABC' || 'DEF'",csv);

	}
	
	@Test
	void addExpressionTest() {
						
		ExpressionList exprList = (ExpressionList) CoreModelFactory.getInstance().createExpressionList();
		
		exprList.addExpression(new Column("column_name"));
		exprList.addExpression(new ParmMarker(Types.SMALLINT,new NumericType(10)));
		exprList.addExpression(SQLTypeFactory.getInstance().mathExpression(SQLTypeFactory.getInstance().constant(100)).divide(10));
		exprList.addExpression(SQLTypeFactory.getInstance().stringExpression(SQLTypeFactory.getInstance().constant("ABC")).concat("DEF"));
		String csv = new TestBaseSQLSerializer().serializeSQLTypeList(exprList.getExpressions());
		Assertions.assertEquals("column_name,?,100 / 10,'ABC' || 'DEF'",csv);		

	}
	
}
