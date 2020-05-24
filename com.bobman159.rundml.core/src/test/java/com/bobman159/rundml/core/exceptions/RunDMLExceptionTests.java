package com.bobman159.rundml.core.exceptions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RunDMLExceptionTests {
	
	private static RunDMLException rdexSqlException;
	private static RunDMLException rdexReflectException;
	private static RunDMLException rdexSqlModelException;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rdexSqlException = RunDMLException.createRunDMLException(null, RunDMLException.SQL_ERROR);
		rdexReflectException = RunDMLException.createRunDMLException(null, RunDMLException.TABLE_ROW_CLASS_REFLECTION);
		rdexSqlModelException = RunDMLException.createRunDMLException(null, RunDMLException.SQL_MODEL_BUILD);
		
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
	void testCreateRunDMLException() {
		rdexSqlException = RunDMLException.createRunDMLException(null, RunDMLException.SQL_ERROR);
		Assertions.assertNotNull(rdexSqlException);
		
		rdexReflectException = RunDMLException.createRunDMLException(null, RunDMLException.TABLE_ROW_CLASS_REFLECTION,
				"com.bobman159.rundml.tableRowClass");
		Assertions.assertNotNull(rdexReflectException);
		
		rdexSqlModelException = RunDMLException.createRunDMLException(null, RunDMLException.SQL_MODEL_BUILD);
		Assertions.assertNotNull(rdexSqlModelException);
		
	}

	@Test
	void testGetRunDMLMessage() {
		String msgSQLError = "RunDML encountered a SQL Exception error during execution";
		String msgReflectError = "RunDML encountered a Table Row Class Reflection error in class com.bobman159.rundml.tableRowClass";
		String msgSqlModelError = "RunDML encountered a Error during build of SQL statement model";
		
		Assertions.assertEquals(msgSQLError, rdexSqlException.getRunDMLMessage());
		Assertions.assertEquals(msgReflectError, rdexReflectException.getRunDMLMessage());
		Assertions.assertEquals(msgSqlModelError,  rdexSqlModelException.getRunDMLMessage());
	}

	@Test
	void testGetExecutionPhase() {
		
		Assertions.assertEquals(RunDMLException.SQL_ERROR,rdexSqlException.getExecutionPhase());
		Assertions.assertEquals(RunDMLException.TABLE_ROW_CLASS_REFLECTION,rdexReflectException.getExecutionPhase());
		Assertions.assertEquals(RunDMLException.SQL_MODEL_BUILD,rdexSqlModelException.getExecutionPhase());
		
	}

}
