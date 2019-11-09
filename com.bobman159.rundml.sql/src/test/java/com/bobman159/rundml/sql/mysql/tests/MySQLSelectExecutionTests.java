package com.bobman159.rundml.sql.mysql.tests;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;
import com.bobman159.rundml.sql.factory.RunDMLSQLFactory;
import com.bobman159.rundml.sql.mysql.mocktables.MockMySQLPrimitivesTypeTest;
import com.bobman159.rundml.sql.mysql.mocktables.MockMySQLStringTypeTest;
import com.bobman159.rundml.sql.mysql.mocktables.MySQLTypeTest;


class MySQLSelectExecutionTests {

	private static Logger logger = LogManager.getLogger(MySQLSelectExecutionTests.class.getName());
	private static DefaultConnectionProvider mySQLProvider;
	
	
	private static final String DFLTINTEGER = "DfltInteger";
	private static final String NOTNULLMEDINT = "NotNullMediumInt";
	private static final String DFLTINTUNSIGNED = "DfltIntUnsigned";
	private static final String DFLTTINYINT = "DfltTinyInt";
	private static final String NOTNULLSMINT = "NotNullSmint";
	private static final String NOTNULLDEC72 = "NotNullDec72";	
	private static final String NOTNULLTIME = "NotNullTime";
	private static final String NOTNULLDATE = "NotNullDate";
	private static final String NOTNULLTSTAMP = "NotNullTimestamp";
	private static final String NOTNULLDATETIME = "NotNullDateTime";
	private static final String NOTNULLVARCHAR = "NotNullVarchar";
	private static final String NOTNULLCHAR = "NotNullChar";
	private static final String NOTNULLBLOB = "NotNullBlob";
	private static final String NOTNULLTEXT = "NotNullText";
	private static final String NOTNULLBOOLEAN = "NotNullBoolean";
	private static final String NOTNULLBIT = "NotNullBit";
	private static final String NOTNULLBIGINT = "NotNullBigInt";
	private static final String NOTNULLBINARY = "NotNullBinary";
	private static final String NOTNULLVARBINARY = "NotNullVarBinary";

	/**
	 * The MySQL database to be used for testing, this should be the database 
	 * where you ran the MySQLRunDMLInit.sql against.
	 */
	static final String MYSQL_DB = "rundml";
	
	/**
	 * The URL for the MySQL database that the MySQLRunDmlInit.sql was executed
	 */

	private static final String MYSQL_DB_URL = "//localhost:3306/" + MYSQL_DB;
	private static final String MYSQLUSER = "RunDmlUser";
	private static final String MYSQL_PASSWORD = "Tgbn6929";
	private static final String NUMBER_CONNECTIONS = "5";
	
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String RUNDML_TABLE = "typetest";
	
	@BeforeAll
	static void setUpBeforeClass() {
		String dir = System.getProperty("user.dir");
		String dbPath = dir + "\\H2Database\\" + "RundmlTest";
		logger.info("dbPath: " + dbPath);
		mySQLProvider = PoolFactory.makeMySQLDataSource(MYSQL_DB_URL, MYSQLUSER, 
				MYSQL_PASSWORD, NUMBER_CONNECTIONS);

	}

	@AfterAll
	static void tearDownAfterClass() {
	} 

	@BeforeEach
	void setUp()  {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void allColumnTypesObjectTest() {
		
		logger.info("****** allColumnTypesObjectTest ******");
		
		List<Object> results = RunDMLSQLFactory.createMySQLSelectStatement()				
				.select()
				.selectExpression(Expression.column(DFLTINTEGER))
				.selectExpression(Expression.column(NOTNULLMEDINT))
				.selectExpression(Expression.column(DFLTINTUNSIGNED))
				.selectExpression(Expression.column(DFLTTINYINT))
				.selectExpression(Expression.column(NOTNULLSMINT))
				.selectExpression(Expression.column(NOTNULLDEC72))
				.selectExpression(Expression.column(NOTNULLTIME))
				.selectExpression(Expression.column(NOTNULLDATE))
				.selectExpression(Expression.column(NOTNULLTSTAMP))
				.selectExpression(Expression.column(NOTNULLDATETIME))
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.selectExpression(Expression.column(NOTNULLCHAR))
				.selectExpression(Expression.column(NOTNULLBLOB))
				.selectExpression(Expression.column(NOTNULLTEXT))
				.selectExpression(Expression.column(NOTNULLBOOLEAN))
				.selectExpression(Expression.column(NOTNULLBIT))
				.selectExpression(Expression.column(NOTNULLBIGINT))
				.selectExpression(Expression.column(NOTNULLBINARY))
				.selectExpression(Expression.column(NOTNULLVARBINARY))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.execute(mySQLProvider.getConnection(),MySQLTypeTest.class);
		
		Assertions.assertEquals(6, results.size());
		
		MySQLTypeTest test = (MySQLTypeTest) results.get(0);
		Assertions.assertEquals(100000,test.getDfltInteger());
		Assertions.assertEquals(100000, test.getNotNullMediumInt());
		Assertions.assertEquals(100000, test.getDfltIntUnsigned());
		Assertions.assertEquals(50, test.getDfltTinyInt());

		short shortVal = 32760;
		Assertions.assertEquals(new Short(shortVal),test.getNotNullSmint());		
		Assertions.assertEquals(12345.10,test.getNotNullDec72().doubleValue()); 		
		Assertions.assertEquals("01:01:01", test.getNotNullTime().toString());
		Assertions.assertEquals("1970-01-01", test.getNotNullDate().toString());		
		
		Assertions.assertEquals("2000-01-01 12:00:00.0",test.getNotNullTimestamp().toString());		
		Assertions.assertEquals("2000-01-01 12:00:00.0",test.getNotNullDateTime().toString());
		Assertions.assertEquals("Abcdefg",test.getNotNullVarchar());
		Assertions.assertEquals("0123456789",test.getNotNullChar());
		
		Assertions.assertNotNull(test.getNotNullBlob());
		Assertions.assertNotNull(test.getNotNullText());
		Assertions.assertEquals(false, test.getNotNullBit());		
		Assertions.assertEquals(true,test.getNotNullBoolean());		 
		
		Assertions.assertEquals(2147483650L, test.getNotNullBigInt());
		byte[] binaryArray = {0b00};
		Assertions.assertArrayEquals(binaryArray,test.getNotNullBinary()); 
		byte[] varBinaryArray = {0b00000000};
		Assertions.assertArrayEquals(varBinaryArray,test.getNotNullVarBinary()); 
		
		MySQLTypeTest test4 = (MySQLTypeTest) results.get(4);
		Assertions.assertEquals(100004,test4.getDfltInteger());
		Assertions.assertEquals(100004, test4.getNotNullMediumInt());
		Assertions.assertEquals(100004, test4.getDfltIntUnsigned());
		Assertions.assertEquals(54, test4.getDfltTinyInt());
		
		short shortVal2 = 32764;
		Assertions.assertEquals(new Short(shortVal2),test4.getNotNullSmint());		
		Assertions.assertEquals(12345.14,test4.getNotNullDec72().doubleValue());
		Assertions.assertEquals("14:14:14", test4.getNotNullTime().toString());
		Assertions.assertEquals("1970-04-04", test4.getNotNullDate().toString());
		
		Assertions.assertEquals("2000-04-04 12:00:00.0",test4.getNotNullTimestamp().toString());
		Assertions.assertEquals("2000-04-04 12:00:00.0",test4.getNotNullDateTime().toString());
		Assertions.assertEquals("Dbcdefg",test4.getNotNullVarchar());		
		Assertions.assertEquals("4123456789",test4.getNotNullChar());
		
		Assertions.assertNotNull(test.getNotNullBlob());
		Assertions.assertNotNull(test.getNotNullText());
		Assertions.assertEquals(false, test4.getNotNullBit());		
		Assertions.assertEquals(true,test4.getNotNullBoolean());		 
		
		Assertions.assertEquals(2147483654L,test4.getNotNullBigInt().longValue());		
		byte[] binaryArray4 = {0b10};
		Assertions.assertArrayEquals(binaryArray4,test4.getNotNullBinary()); 
		byte[] varBinaryArray4 = {0b00001111};
		Assertions.assertArrayEquals(varBinaryArray4,test4.getNotNullVarBinary()); 
	
		MySQLTypeTest test5 = (MySQLTypeTest) results.get(5);
		Assertions.assertEquals(0, test5.getDfltInteger());
		Assertions.assertEquals(100005, test5.getNotNullMediumInt());
		Assertions.assertEquals(0,test5.getDfltIntUnsigned());
		Assertions.assertEquals(0,test5.getDfltTinyInt());
		
		short shortVal5 = 32765;
		Assertions.assertEquals(new Short(shortVal5),test5.getNotNullSmint());		
		Assertions.assertEquals(12345.15,test5.getNotNullDec72().doubleValue()); 		
		Assertions.assertEquals("15:15:15", test5.getNotNullTime().toString());
		Assertions.assertEquals("1970-05-05", test5.getNotNullDate().toString());
		
		Assertions.assertEquals("2000-04-04 12:00:00.0",test5.getNotNullTimestamp().toString());
		Assertions.assertEquals("2000-04-04 12:00:00.0",test5.getNotNullDateTime().toString());
		Assertions.assertEquals("Dbcdefh",test5.getNotNullVarchar());
		Assertions.assertEquals("5123456789",test5.getNotNullChar());
		
		Assertions.assertNotNull(test.getNotNullBlob());
		Assertions.assertNotNull(test.getNotNullText());		
		Assertions.assertEquals(true, test5.getNotNullBit());
		Assertions.assertEquals(false,test5.getNotNullBoolean());		 

		Assertions.assertEquals(2147483655L,test5.getNotNullBigInt());

		byte[] binaryArray5 = {0b11};
		Assertions.assertArrayEquals(binaryArray5,test5.getNotNullBinary()); 
		byte[] varBinaryArray5 = {0b00001111};
		Assertions.assertArrayEquals(varBinaryArray5,test5.getNotNullVarBinary()); 

	}
	
	@Test
	void allColumnTypesPrimitivesTest() {

		logger.info("****** allColumnTypesPrimitivesTest ******");
		List<Object> results = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(DFLTINTEGER))
				.selectExpression(Expression.column(NOTNULLMEDINT))
				.selectExpression(Expression.column(DFLTINTUNSIGNED))
				.selectExpression(Expression.column(DFLTTINYINT))
				.selectExpression(Expression.column(NOTNULLSMINT))
				.selectExpression(Expression.column(NOTNULLDEC72))
				.selectExpression(Expression.column(NOTNULLTIME))
				.selectExpression(Expression.column(NOTNULLDATE))
				.selectExpression(Expression.column(NOTNULLTSTAMP))
				.selectExpression(Expression.column(NOTNULLDATETIME))
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.selectExpression(Expression.column(NOTNULLCHAR))
				.selectExpression(Expression.column(NOTNULLBLOB))
				.selectExpression(Expression.column(NOTNULLTEXT))
				.selectExpression(Expression.column(NOTNULLBOOLEAN))
				.selectExpression(Expression.column(NOTNULLBIT))
				.selectExpression(Expression.column(NOTNULLBIGINT))
				.selectExpression(Expression.column(NOTNULLBINARY))
				.selectExpression(Expression.column(NOTNULLVARBINARY))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.execute(mySQLProvider.getConnection(),MockMySQLPrimitivesTypeTest.class);
		
		Assertions.assertEquals(6, results.size());
		
		MockMySQLPrimitivesTypeTest test3 = (MockMySQLPrimitivesTypeTest) results.get(3);
		Assertions.assertEquals(100003,test3.getDfltInteger());
		Assertions.assertEquals(100003, test3.getMedIntNotNull());
		Assertions.assertEquals(100003, test3.getUnsignedDflt());
		Assertions.assertEquals(53, test3.getTinyIntDflt());


		short shortVal = 32763;
		Assertions.assertEquals(new Short(shortVal),test3.getNotNullSmint());		
		Assertions.assertEquals(12345.13,test3.getNotNullDec72().doubleValue()); 		
		Assertions.assertEquals("13:13:13", test3.getTimeNotNull().toString());
		Assertions.assertEquals("1970-03-03", test3.getNotNullDate().toString());		
		
		Assertions.assertEquals("2000-03-03 12:00:00.0",test3.getNotNullTimestamp().toString());		
		Assertions.assertEquals("2000-03-03 12:00:00.0",test3.getNotNullDateTime().toString());
		Assertions.assertEquals("Dbcdefg",test3.getNotNullVarchar());
		Assertions.assertEquals("3123456789",test3.getCharNotNull());
		
		Assertions.assertNotNull(test3.getNotNullBlob());
		Assertions.assertNotNull(test3.getLobCharCol());
		Assertions.assertEquals(false, test3.getBitNotNull());		
		Assertions.assertEquals(true,test3.getBooleanNotNull());		 
		
		Assertions.assertEquals(2147483653L, test3.getDfltBigInt());
		byte[] binaryArray = {0b11};
		Assertions.assertArrayEquals(binaryArray,test3.getNotNullBinary()); 
		byte[] varBinaryArray = {0b00000111};
		Assertions.assertArrayEquals(varBinaryArray,test3.getNotNullVarBinary()); 		

	}
	
	@Test
	void allColumnTypesStringsTest() {

		logger.info("****** allColumnTypesStringsTest ******");
		List<Object> results = RunDMLSQLFactory.createMySQLSelectStatement()
				.select()
				.selectExpression(Expression.column(DFLTINTEGER))
				.selectExpression(Expression.column(NOTNULLMEDINT))
				.selectExpression(Expression.column(DFLTINTUNSIGNED))
				.selectExpression(Expression.column(DFLTTINYINT))
				.selectExpression(Expression.column(NOTNULLSMINT))
				.selectExpression(Expression.column(NOTNULLDEC72))
				.selectExpression(Expression.column(NOTNULLTIME))
				.selectExpression(Expression.column(NOTNULLDATE))
				.selectExpression(Expression.column(NOTNULLTSTAMP))
				.selectExpression(Expression.column(NOTNULLDATETIME))
				.selectExpression(Expression.column(NOTNULLVARCHAR))
				.selectExpression(Expression.column(NOTNULLCHAR))
				.selectExpression(Expression.column(NOTNULLBLOB))
				.selectExpression(Expression.column(NOTNULLTEXT))
				.selectExpression(Expression.column(NOTNULLBOOLEAN))
				.selectExpression(Expression.column(NOTNULLBIT))
				.selectExpression(Expression.column(NOTNULLBIGINT))
				.selectExpression(Expression.column(NOTNULLBINARY))
				.selectExpression(Expression.column(NOTNULLVARBINARY))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.execute(mySQLProvider.getConnection(),MockMySQLStringTypeTest.class);
		
		Assertions.assertEquals(6, results.size());
		
		MockMySQLStringTypeTest test2 = (MockMySQLStringTypeTest) results.get(2);
		Assertions.assertEquals("100002",test2.getDfltInteger());
		Assertions.assertEquals("100002", test2.getMedIntNotNull());
		Assertions.assertEquals("100002", test2.getUnsignedDflt());
		Assertions.assertEquals("52", test2.getTinyIntDflt());

		Assertions.assertEquals("32762",test2.getNotNullSmint());
		Assertions.assertEquals("12345.12",test2.getNotNullDec72()); 		
		Assertions.assertEquals("12:12:12", test2.getTimeNotNull());
		Assertions.assertEquals("1970-02-02", test2.getNotNullDate());		
		
		Assertions.assertEquals("2000-02-02 12:00:00",test2.getNotNullTimestamp());		
		Assertions.assertEquals("2000-02-02 12:00:00",test2.getNotNullDateTime());
		Assertions.assertEquals("Cbcdefg",test2.getNotNullVarchar());
		Assertions.assertEquals("2123456789",test2.getCharNotNull());
		
		Assertions.assertEquals("2123456789",test2.getNotNullBlob());
		Assertions.assertEquals("Abcdefghijklmnor",test2.getLobCharCol());
		Assertions.assertEquals("0", test2.getBitNotNull());		
		Assertions.assertEquals("1",test2.getBooleanNotNull());		 
		
		Assertions.assertEquals("2147483652", test2.getNotNullBigInt());
		Assertions.assertEquals("\u0001",test2.getBinaryNotNull()); 
		Assertions.assertEquals("\u0003",test2.getNotNullVarBinary()); 

	}
	
	/*
	 * 	Remaining Tests:
	 * 
	 *	*	All database types for parameter markers
	 *
	 *	*	Expressions in SELECT list (string, numeric)
	 *	
	 *	*	Exception thrown by SelectCallable.call() during fetch
	 *
	 *	*	SELECT ResultSet has ALL different column names than 
	 *		field names
	 *
	 *	*	SELECT ResultSet has some different column names than
	 *		field names
	 *
	 *	*	Test Mapping to non default types:
	 *		*	ResultSet type String to map class type INTEGER (and other numeric types)			
	 *		*	ResultSet type INTEGER (and other numeric types) to class type String
	 *		*	ResultSet type Date,Time,Timestamp to class type String
	 *		*	ResultSet type Clob,Blob to class type String
	 *		*	ResultSet type Char,Varchar to class type int (expect an error?)
	 *	
	 */

}
