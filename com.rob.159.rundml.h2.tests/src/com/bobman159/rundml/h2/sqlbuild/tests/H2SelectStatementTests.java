package com.bobman159.rundml.h2.sqlbuild.tests;

import java.sql.Connection;
import java.sql.Types;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.h2.sql.builder.stmts.H2SQLStatement;
import com.bobman159.rundml.h2.sql.builder.stmts.H2SelectStatement;

/* 
 * The SQL syntax checks should be verified as executable in H2
 */
class H2SelectStatementTests {

	private static TableDefinition tbDef;
	private static Connection conn;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		tbDef = new TableDefinition("rundml","typetest");
		tbDef.addColumn("dfltInteger", Types.INTEGER);
		tbDef.addColumn("dfltSigned", Types.INTEGER);
		tbDef.addColumn("dfltTinyInt", Types.TINYINT);
		tbDef.addColumn("NotNullVarchar", Types.VARCHAR);
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
	void h2SelectTest() {
		
		String stmtText = ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
					  .selectStar()
					  .from("rundml","typetest"))
					  .toStmt();
		
		Assert.assertEquals("select * from rundml.typetest",stmtText);
		

	}
	
	@Test
	void h2SelectTableDefinitionTest() {
		String stmtText =  ((H2SelectStatement) H2SQLStatement.selectStatement(conn)
				  .select(tbDef))
				  .toStmt();

		Assert.assertEquals("select DFLTINTEGER, DFLTSIGNED, " + 
							"DFLTTINYINT,NOTNULLVARCHR "	   + 
							"from rundml.typetest",stmtText);		
	}
	
//	@Test
//	void h2SelectExpressionTest() {
//		//Test each of the supported IExpression types
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectTopTest() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void h2SelectTopDistinctTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectTopAllTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectDistinctAllTest() {
//		//SELECT DISTINCT ALL is not valid in H2
//		//This test should return no rows  (possibly throw an exception?)
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectWhereTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectGroupByTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectOrderByTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectHavingTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectLimitTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectLimitOffsetTest() {
////		SELECT * 
////		FROM RUNDML.TYPETEST
////		LIMIT 5 OFFSET 1;
//
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectLimitSampleSizeTest() {
////		SELECT * 
////		FROM RUNDML.TYPETEST
////		LIMIT 5 sample_size 3;
//
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	void h2SelectLimitOffsetSampleSizeTest() {
////		SELECT * 
////		FROM RUNDML.TYPETEST
////		LIMIT 5 OFFSET 1 sample_size 3;
//
//		fail("Not yet implemented");
//	}


	
}
