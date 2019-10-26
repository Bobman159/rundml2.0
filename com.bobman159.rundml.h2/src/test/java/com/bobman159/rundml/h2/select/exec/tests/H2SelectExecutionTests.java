package com.bobman159.rundml.h2.select.exec.tests;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.h2.H2SQLStatement;
import com.bobman159.rundml.h2.mocktables.H2MockPrimitivesTypeTest;
import com.bobman159.rundml.h2.mocktables.H2MockStringTypeTest;
import com.bobman159.rundml.h2.mocktables.TypeTest;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;
import com.bobman159.rundml.jdbc.select.ITableRow;

class H2SelectExecutionTests {

	//TODO: Implement Select Execution for H2 Database
	/*
	 * #1 	A class that implements ITableRow interface will be used to 
	 * 		contain the data from the SELECT statement results. By default
	 *		the class name will be the same name as the table.
	 * 		#1.a	Alternatively, the TableDefintion constructor can define 
	 * 				the ITableRow implementing class that will be used to 
	 * 				contain the rows from the SELECT result
	 *  #2	By default the implementor of ITableRow will contain fields with 
	 *  	the same names as the column names defined in the TableDefinition.
	 *  	#2.a	Alternatively, the TableDefinition addColumn method can 
	 *  			be used to specify a string value for the field name of the
	 *  			ITableRow implementor class that will contain the value 
	 *  			from the results.
	 *  	#2.b	setXXXX() methods will need to be defined so that runDML 
	 *  			can call the setXXXX() method to set the value of the field.
	 *  			XXXX will equal the name of the field as define in either 
	 *  			#2 or #2.a
	 *  #3	While processing the ResultSet for the select, the ITableRow 
	 *  	implementor getXXXX() methods will be called via java.lang.reflect
	 *  	to set the value of the field(s) present in the ResultSet.
	 */

	private static DefaultConnectionProvider h2Provider;
	/**
	 * H2 User Id, change this value if you want to use a different user Id
	 */
	private static final String H2USER = "DmlTestUser";
	
	/**
	 * H2 User Id password, change this value if you want to use a different password
	 */
	private static final String H2PASSWORD = "Tgbn6929";
		
	private static final String NUMBER_CONNECTIONS = "5";
	private static Logger logger = LogManager.getLogger(H2SelectExecutionTests.class.getName());
	
	private static final String DFLTINTEGER = "DfltInteger";
	private static final String NOTNULLMEDINT = "NotNullMediumInt";
	private static final String DFLTSIGNED = "DfltSigned";
	private static final String DFLTTINYINT = "DfltTinyInt";
	private static final String NOTNULLSMINT = "NotNullSmint";
	private static final String NOTNULLDEC72 = "NotNullDec72";	
	private static final String DFLTNUM72 = "DfltNumber72";
	private static final String NOTNULLTIME = "NotNullTime";
	private static final String NOTNULLDATE = "NotNullDate";
	private static final String NOTNULLTSTAMP = "NotNullTimestamp";
	private static final String NOTNULLDATETIME = "NotNullDateTime";
	private static final String NOTNULLVARCHAR = "NotNullVarchar";
	private static final String NOTNULLCHAR = "NotNullChar";
	private static final String DFLTBLOB = "DfltBlob";
	private static final String DFLTCLOB = "DfltClob";
	private static final String NOTNULLBOOLEAN = "NotNullBoolean";
	private static final String NOTNULLBOOL = "NotNullBool";
	private static final String NOTNULLBIT = "NotNullBit";
	private static final String DFLTBIGINT = "DfltBigInt";
	private static final String DFLTINT8 = "DfltInt8";
	private static final String NOTNULLIDENTITY = "NotNullIdentity";
	
	private static final String RUNDML_SCHEMA = "rundml";
	private static final String RUNDML_TABLE = "typetest";
	
	//DEBUG
	private List<ITableRow> testRows;

	
	private static TableDefinition tbDef;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		String dir = System.getProperty("user.dir");
		String dbPath = dir + "\\H2Database\\" + "RundmlTest";
		logger.info("dbPath: " + dbPath);
		h2Provider = PoolFactory.makeH2DataSource(dbPath, H2USER, H2PASSWORD, 
				NUMBER_CONNECTIONS);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		h2Provider.closePool();
	}
	

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/*
	 *	*	All database column types
	 *	*	Defaults for field names
	 *	*	Should use class TypeTest.java 
	 *
	 */
	@Test
	void allColumnTypesObjectTest() {

		tbDef = typeTestObjectsTableDef();
		
		logger.info("****** allColumnTypesObjectTest ******");
		List<ITableRow> results = H2SQLStatement.selectStatement(h2Provider.getConnection(),tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLMEDINT))
				.selectExpression(tbDef.column(DFLTSIGNED))
				.selectExpression(tbDef.column(DFLTTINYINT))
				.selectExpression(tbDef.column(NOTNULLSMINT))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(DFLTNUM72))
				.selectExpression(tbDef.column(NOTNULLTIME))
				.selectExpression(tbDef.column(NOTNULLDATE))
				.selectExpression(tbDef.column(NOTNULLTSTAMP))
				.selectExpression(tbDef.column(NOTNULLDATETIME))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.selectExpression(tbDef.column(DFLTBLOB))
				.selectExpression(tbDef.column(DFLTCLOB))
				.selectExpression(tbDef.column(NOTNULLBOOLEAN))
				.selectExpression(tbDef.column(NOTNULLBOOL))
				.selectExpression(tbDef.column(NOTNULLBIT))
				.selectExpression(tbDef.column(DFLTBIGINT))
				.selectExpression(tbDef.column(DFLTINT8))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.fetch();
		
		Assertions.assertEquals(6, results.size());
		
		TypeTest test = (TypeTest) results.get(0);
		Assertions.assertEquals(100000,test.getDfltInteger());
		Assertions.assertEquals(100000, test.getNotNullMediumInt());
		Assertions.assertEquals(100000, test.getDfltSigned());
		Assertions.assertEquals(50, test.getDfltTinyInt().intValue());
		
		short shortVal = 32767;
		Assertions.assertEquals(new Short(shortVal),test.getNotNullSmint());		
		Assertions.assertEquals(12345.10,test.getNotNullDec72().doubleValue()); 
		Assertions.assertEquals(12345.10,test.getDfltNumber72().doubleValue());						
		
		Assertions.assertEquals("01:01:01", test.getNotNullTime().toString());
		Assertions.assertEquals("1970-01-01", test.getNotNullDate().toString());		
		Assertions.assertEquals("2000-01-01 12:00:00.0",test.getNotNullTimestamp().toString());
		
		Assertions.assertEquals("2000-01-01 12:00:00.0",test.getNotNullDateTime().toString());
		Assertions.assertEquals("Abcdefg",test.getNotNullVarchar());
		Assertions.assertEquals("0123456789",test.getNotNullChar());
		
		Assertions.assertNotNull(test.getDfltBlob());
		Assertions.assertNotNull(test.getDfltClob());
		
		Assertions.assertEquals(false,test.getNotNullBoolean());		 
		
		Assertions.assertEquals(false, test.getNotNullBool());
		Assertions.assertEquals(false, test.getNotNullBit());
		
		Assertions.assertEquals(2147483650L,test.getDfltBigInt().longValue());
		Assertions.assertEquals(2147483650L,test.getDfltInt8().longValue()); 
		
		TypeTest test4 = (TypeTest) results.get(4);
		Assertions.assertEquals(100004,test4.getDfltInteger());
		Assertions.assertEquals(100004, test4.getNotNullMediumInt());
		Assertions.assertEquals(100004, test4.getDfltSigned());
		Assertions.assertEquals(54, test4.getDfltTinyInt().intValue());
		
		short shortVal2 = 32767;
		Assertions.assertEquals(new Short(shortVal2),test4.getNotNullSmint());		
		Assertions.assertEquals(12345.14,test4.getNotNullDec72().doubleValue()); 
		Assertions.assertEquals(12345.14,test4.getDfltNumber72().doubleValue());						
		
		Assertions.assertEquals("14:14:14", test4.getNotNullTime().toString());
		Assertions.assertEquals("1970-04-04", test4.getNotNullDate().toString());
		
		Assertions.assertEquals("2000-04-04 12:00:00.0",test4.getNotNullTimestamp().toString());
		Assertions.assertEquals("2000-04-04 12:00:00.0",test4.getNotNullDateTime().toString());
		Assertions.assertEquals("4123456789",test4.getNotNullChar());
		
		Assertions.assertEquals("Abcdefk",test4.getNotNullVarchar());
		Assertions.assertNotNull(test.getDfltBlob());
		Assertions.assertNotNull(test.getDfltClob());
		
		Assertions.assertEquals(true,test4.getNotNullBoolean());		 
		Assertions.assertEquals(true, test4.getNotNullBool());
		Assertions.assertEquals(false, test4.getNotNullBit());
		
		Assertions.assertEquals(2147483654L,test4.getDfltBigInt().longValue());
		Assertions.assertEquals(2147483654L,test4.getDfltInt8().longValue()); 
	
		TypeTest test5 = (TypeTest) results.get(5);
		Assertions.assertEquals(0, test5.getDfltInteger());
		Assertions.assertEquals(100005, test5.getNotNullMediumInt());
		Assertions.assertEquals(0,test5.getDfltSigned());
		Assertions.assertEquals(0,test5.getDfltTinyInt().intValue());
		
		short shortVal5 = 32767;
		Assertions.assertEquals(new Short(shortVal5),test5.getNotNullSmint());		
		Assertions.assertEquals(12345.15,test5.getNotNullDec72().doubleValue()); 
		Assertions.assertNull(test5.getDfltNumber72());						
		
		Assertions.assertEquals("15:15:15", test5.getNotNullTime().toString());
		Assertions.assertEquals("1970-12-12", test5.getNotNullDate().toString());
		
		Assertions.assertEquals("2000-12-12 12:12:12.0",test5.getNotNullTimestamp().toString());
		Assertions.assertEquals("2000-12-12 12:12:12.0",test5.getNotNullDateTime().toString());
		Assertions.assertEquals("5123456789",test5.getNotNullChar());
		
		Assertions.assertEquals("Abcdefl",test5.getNotNullVarchar());
		Assertions.assertNotNull(test.getDfltBlob());
		Assertions.assertNotNull(test.getDfltClob());
		
		Assertions.assertEquals(true,test5.getNotNullBoolean());		 
		Assertions.assertEquals(true, test5.getNotNullBool());
		Assertions.assertEquals(false, test5.getNotNullBit());
		
		Assertions.assertEquals(0,test5.getDfltBigInt());
		Assertions.assertEquals(0, test5.getDfltInt8()); 
	}
	
	/*
	 *	*	Default for field names
	 *	*	Should use 
	 */
	@Test
	void allColumnTypesPrimitivesTest() {

		logger.info("****** allColumnTypesPrimitivesTest ******");
		tbDef = mockTypeTestPrimitivesTableDef();
		List<ITableRow> results = new ArrayList<ITableRow>();
		
		results = H2SQLStatement
				.selectStatement(h2Provider.getConnection(),tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLMEDINT))
				.selectExpression(tbDef.column(DFLTSIGNED))
				.selectExpression(tbDef.column(DFLTTINYINT))
				.selectExpression(tbDef.column(NOTNULLSMINT))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(DFLTNUM72))
				.selectExpression(tbDef.column(NOTNULLTIME))
				.selectExpression(tbDef.column(NOTNULLDATE))
				.selectExpression(tbDef.column(NOTNULLTSTAMP))
				.selectExpression(tbDef.column(NOTNULLDATETIME))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.selectExpression(tbDef.column(DFLTBLOB))
				.selectExpression(tbDef.column(DFLTCLOB))
				.selectExpression(tbDef.column(NOTNULLBOOLEAN))
				.selectExpression(tbDef.column(NOTNULLBOOL))
				.selectExpression(tbDef.column(NOTNULLBIT))
				.selectExpression(tbDef.column(DFLTBIGINT))
				.selectExpression(tbDef.column(DFLTINT8))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.fetch();

		Assertions.assertEquals(6, results.size());
		
		H2MockPrimitivesTypeTest test1 = (H2MockPrimitivesTypeTest) results.get(1);
		Assertions.assertEquals(100001,test1.getDfltInteger());
		Assertions.assertEquals(100001, test1.getMedIntNotNull());
		Assertions.assertEquals(100001, test1.getSignedDflt());
		Assertions.assertEquals(51, test1.getTinyIntDflt());
		
		short shortVal3 = 32766;
		Assertions.assertEquals(new Short(shortVal3),test1.getNotNullSmint());		
		Assertions.assertEquals(12345.11,test1.getNotNullDec72().doubleValue()); 
		Assertions.assertEquals(12345.11,test1.getNum72Dflt().doubleValue());						
		
		Assertions.assertEquals("11:11:11", test1.getTimeNotNull().toString());
		Assertions.assertEquals("2019-01-01", test1.getNotNullDate().toString());
		
		Assertions.assertEquals("2019-01-01 12:00:00.0",test1.getNotNullTimestamp().toString());
		Assertions.assertEquals("2019-01-01 12:00:00.0",test1.getNotNullDateTime().toString());
		Assertions.assertEquals("1123456789",test1.getCharNotNull());
		
		Assertions.assertEquals("Abcdefh",test1.getNotNullVarchar());
//		Assertions.assertEquals("blob3: X'3123456789'",test1.getDfltBlob().toString());
//		Assertions.assertEquals("clob3: 'Abcdefghijklmnos'",test1.getLobCharCol().toString());
		
		Assertions.assertEquals(false,test1.getBooleanNotNull());		 
		Assertions.assertEquals(false, test1.getNotNullBool());
		Assertions.assertEquals(true, test1.getBitNotNull());
		
		Assertions.assertEquals(2147483651L,test1.getDfltBigInt());
		Assertions.assertEquals(2147483651L,test1.getDflt8Col()); 


	}
	
	/*
	 *	*	Specifies mapping class, some Field Names default and 
	 *		others specified.
	 *	*	Should use MockTypeTest2.java
	 *
     */
	@Test
	void allColumnTypesStringTest() {
		
		logger.info("****** allColumnTypesStringTest ******");
		tbDef = mockTypeTestStringTableDef();
		
		List<ITableRow> results = H2SQLStatement
				.selectStatement(h2Provider.getConnection(),tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLMEDINT))
				.selectExpression(tbDef.column(DFLTSIGNED))
				.selectExpression(tbDef.column(DFLTTINYINT))
				.selectExpression(tbDef.column(NOTNULLSMINT))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(DFLTNUM72))
				.selectExpression(tbDef.column(NOTNULLTIME))
				.selectExpression(tbDef.column(NOTNULLDATE))
				.selectExpression(tbDef.column(NOTNULLTSTAMP))
				.selectExpression(tbDef.column(NOTNULLDATETIME))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.selectExpression(tbDef.column(DFLTBLOB))
				.selectExpression(tbDef.column(DFLTCLOB))
				.selectExpression(tbDef.column(NOTNULLBOOLEAN))
				.selectExpression(tbDef.column(NOTNULLBOOL))
				.selectExpression(tbDef.column(NOTNULLBIT))
				.selectExpression(tbDef.column(DFLTBIGINT))
				.selectExpression(tbDef.column(DFLTINT8))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.fetch();
		
		Assertions.assertEquals(6, results.size());
		
		H2MockStringTypeTest test3 = (H2MockStringTypeTest) results.get(3);
		Assertions.assertEquals("100003",test3.getIntDflt());
		Assertions.assertEquals("100003", test3.getMedIntNotNull());
		Assertions.assertEquals("100003", test3.getSignedDflt());
		Assertions.assertEquals("53", test3.getTinyIntDflt());
		
		Assertions.assertEquals("32766",test3.getSmintNotNull());		
		Assertions.assertEquals("12345.13",test3.getDec72NotNull()); 
		Assertions.assertEquals("12345.13",test3.getNum72Dflt());						
		
		Assertions.assertEquals("13:13:13", test3.getTimeNotNull());
		Assertions.assertEquals("1970-03-03", test3.getDateNotNull());
		
		Assertions.assertEquals("2000-03-03 12:00:00",test3.getTstampNotNull());
		Assertions.assertEquals("2000-03-03 12:00:00",test3.getDateTstampNotNull());
		Assertions.assertEquals("3123456789",test3.getCharNotNull());
		
		Assertions.assertEquals("Abcdefj",test3.getVarcharNotNull());
		Assertions.assertEquals("3123456789",test3.getBlobCol());
		Assertions.assertEquals("Abcdefghijklmnos",test3.getClobCol());
		
		Assertions.assertEquals("TRUE",test3.getBooleanNotNull());		 
		Assertions.assertEquals("FALSE", test3.getBoolNotNull());
		Assertions.assertEquals("FALSE", test3.getBitNotNull());
		
		Assertions.assertEquals("2147483653",test3.getBigIntDflt());
		Assertions.assertEquals("2147483653",test3.getInt8Dflt()); 
		
	}
	
	/*
	 *	*	Specifies mapping class, some Field Names default and 
	 *		others specified.
	 *	*	Should use MockTypeTest3.java
	 *
     */
	@Test
	void mappingClassAllFieldsTest() {
		
		logger.info("****** mappingClassAllFieldsTest ******");
		tbDef = mockTypeTestStringTableDef();
		
		List<ITableRow> results = H2SQLStatement
				.selectStatement(h2Provider.getConnection(),tbDef)
				.select()
				.selectExpression(tbDef.column(DFLTINTEGER))
				.selectExpression(tbDef.column(NOTNULLMEDINT))
				.selectExpression(tbDef.column(DFLTSIGNED))
				.selectExpression(tbDef.column(DFLTTINYINT))
				.selectExpression(tbDef.column(NOTNULLSMINT))
				.selectExpression(tbDef.column(NOTNULLDEC72))
				.selectExpression(tbDef.column(DFLTNUM72))
				.selectExpression(tbDef.column(NOTNULLTIME))
				.selectExpression(tbDef.column(NOTNULLDATE))
				.selectExpression(tbDef.column(NOTNULLTSTAMP))
				.selectExpression(tbDef.column(NOTNULLDATETIME))
				.selectExpression(tbDef.column(NOTNULLVARCHAR))
				.selectExpression(tbDef.column(NOTNULLCHAR))
				.selectExpression(tbDef.column(DFLTBLOB))
				.selectExpression(tbDef.column(DFLTCLOB))
				.selectExpression(tbDef.column(NOTNULLBOOLEAN))
				.selectExpression(tbDef.column(NOTNULLBOOL))
				.selectExpression(tbDef.column(NOTNULLBIT))
				.selectExpression(tbDef.column(DFLTBIGINT))
				.selectExpression(tbDef.column(DFLTINT8))
				.from(RUNDML_SCHEMA,RUNDML_TABLE)
				.fetch();
		
		Assertions.assertEquals(6, results.size());
		
		H2MockStringTypeTest test2 = (H2MockStringTypeTest) results.get(2);
		Assertions.assertEquals("100002",test2.getIntDflt());
		Assertions.assertEquals("100002", test2.getMedIntNotNull());
		Assertions.assertEquals("100002", test2.getSignedDflt());
		Assertions.assertEquals("52", test2.getTinyIntDflt());
		
		Assertions.assertEquals("32765",test2.getSmintNotNull());		
		Assertions.assertEquals("12345.12",test2.getDec72NotNull()); 
		Assertions.assertEquals("12345.12",test2.getNum72Dflt());						
		
		Assertions.assertEquals("12:12:12", test2.getTimeNotNull());
		Assertions.assertEquals("1970-02-02", test2.getDateNotNull());
		
		Assertions.assertEquals("2000-02-02 12:00:00",test2.getTstampNotNull());
		Assertions.assertEquals("2000-02-02 12:00:00",test2.getDateTstampNotNull());
		Assertions.assertEquals("2123456789",test2.getCharNotNull());
		
		Assertions.assertEquals("Abcdefi",test2.getVarcharNotNull());
//		Assertions.assertEquals("blob3: X'3123456789'",test1.getDfltBlob().toString());
//		Assertions.assertEquals("clob3: 'Abcdefghijklmnos'",test1.getLobCharCol().toString());
		
		Assertions.assertEquals("FALSE",test2.getBooleanNotNull());		 
		Assertions.assertEquals("TRUE", test2.getBoolNotNull());
		Assertions.assertEquals("FALSE", test2.getBitNotNull());
		
		Assertions.assertEquals("2147483652",test2.getBigIntDflt());
		Assertions.assertEquals("2147483652",test2.getInt8Dflt()); 
		
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
	
	/*
	 *	Creates a default TableMapper for the rundml.typetest table
	 * 		*	No specified mapping for the table class fields
	 */
	private static TableDefinition typeTestObjectsTableDef() {
		
		TableDefinition tbDef = new TableDefinition("rundml","typetest",TypeTest.class);
		tbDef.addColumn(DFLTINTEGER , Types.INTEGER);  
		tbDef.addColumn(NOTNULLMEDINT,Types.INTEGER);
		tbDef.addColumn(DFLTSIGNED, Types.INTEGER);
		tbDef.addColumn(DFLTTINYINT, Types.TINYINT);
		tbDef.addColumn(NOTNULLSMINT, Types.SMALLINT);
		tbDef.addColumn(NOTNULLDEC72,Types.DECIMAL);	
		tbDef.addColumn(DFLTNUM72,Types.DECIMAL);
		tbDef.addColumn(NOTNULLTIME,Types.TIME);
		tbDef.addColumn(NOTNULLDATE,Types.DATE);
		tbDef.addColumn(NOTNULLTSTAMP,Types.TIMESTAMP);
		tbDef.addColumn(NOTNULLDATETIME,Types.TIMESTAMP);
		tbDef.addColumn(NOTNULLVARCHAR,Types.VARCHAR);
		tbDef.addColumn(NOTNULLCHAR,Types.CHAR);
		tbDef.addColumn(DFLTBLOB,Types.BLOB);
		tbDef.addColumn(DFLTCLOB,Types.CLOB);
		tbDef.addColumn(NOTNULLBOOLEAN,Types.BOOLEAN);
		tbDef.addColumn(NOTNULLBOOL,Types.BOOLEAN);
		tbDef.addColumn(NOTNULLBIT,Types.BIT);
		tbDef.addColumn(DFLTBIGINT,Types.BIGINT);
		tbDef.addColumn(DFLTINT8,Types.BIGINT);
		tbDef.addColumn(NOTNULLIDENTITY,Types.ROWID);
		
		return tbDef;
		
	}
	
	/*
	 *	Creates a default TableDefinition for the rundml.typetest table
	 * 		*	Specifies some mapping for the table class fields
	 * 
	 */
	private static TableDefinition mockTypeTestPrimitivesTableDef() {
		
		TableDefinition tbDef = new TableDefinition("rundml","typetest", 
					H2MockPrimitivesTypeTest.class);
		tbDef.addColumn(DFLTINTEGER , Types.INTEGER);  
		tbDef.addMapColumn(NOTNULLMEDINT,Types.INTEGER,"medIntNotNull");
		tbDef.addMapColumn(DFLTSIGNED, Types.INTEGER,"signedDflt");
		tbDef.addMapColumn(DFLTTINYINT, Types.TINYINT,"tinyIntDflt");
		tbDef.addColumn(NOTNULLSMINT, Types.SMALLINT);
		tbDef.addColumn(NOTNULLDEC72,Types.DECIMAL);	
		tbDef.addMapColumn(DFLTNUM72,Types.DECIMAL,"num72Dflt");
		tbDef.addMapColumn(NOTNULLTIME,Types.TIME,"timeNotNull");
		tbDef.addColumn(NOTNULLDATE,Types.DATE);
		tbDef.addColumn(NOTNULLTSTAMP,Types.TIMESTAMP);
		tbDef.addColumn(NOTNULLDATETIME,Types.TIMESTAMP);
		tbDef.addColumn(NOTNULLVARCHAR,Types.VARCHAR);
		tbDef.addMapColumn(NOTNULLCHAR,Types.CHAR,"charNotNull");
		tbDef.addColumn(DFLTBLOB,Types.BLOB);
		tbDef.addMapColumn(DFLTCLOB,Types.CLOB,"lobCharCol");
		tbDef.addMapColumn(NOTNULLBOOLEAN,Types.BOOLEAN,"booleanNotNull");
		tbDef.addColumn(NOTNULLBOOL,Types.BOOLEAN);
		tbDef.addMapColumn(NOTNULLBIT,Types.BIT,"bitNotNull");
		tbDef.addColumn(DFLTBIGINT,Types.BIGINT);
		tbDef.addMapColumn(DFLTINT8,Types.BIGINT,"dflt8Col");
		tbDef.addColumn(NOTNULLIDENTITY,Types.ROWID);
		
		return tbDef;
	}
	
	/*
	 *	Creates a default TableDefinition for the rundml.typetest table
	 * 		*	Specifies a mapping class name
	 * 		*	Specifies mapping for all the table class fields
	 * 
	 */
	private static TableDefinition mockTypeTestStringTableDef() {
		
		TableDefinition tbDef = new TableDefinition("rundml","typetest", 
					H2MockStringTypeTest.class);
		tbDef.addMapColumn(DFLTINTEGER , Types.INTEGER,"intDflt");  
		tbDef.addMapColumn(NOTNULLMEDINT,Types.INTEGER,"medIntNotNull");
		tbDef.addMapColumn(DFLTSIGNED, Types.INTEGER,"signedDflt");
		tbDef.addMapColumn(DFLTTINYINT, Types.TINYINT,"tinyIntDflt");
		tbDef.addMapColumn(NOTNULLSMINT, Types.SMALLINT,"smintNotNull");
		tbDef.addMapColumn(NOTNULLDEC72,Types.DECIMAL,"dec72NotNull");	
		tbDef.addMapColumn(DFLTNUM72,Types.DECIMAL,"num72Dflt");
		tbDef.addMapColumn(NOTNULLTIME,Types.TIME,"timeNotNull");
		tbDef.addMapColumn(NOTNULLDATE,Types.DATE,"dateNotNull");
		tbDef.addMapColumn(NOTNULLTSTAMP,Types.TIMESTAMP,"tstampNotNull");
		tbDef.addMapColumn(NOTNULLDATETIME,Types.TIMESTAMP,"dateTstampNotNull");
		tbDef.addMapColumn(NOTNULLVARCHAR,Types.VARCHAR,"varcharNotNull");
		tbDef.addMapColumn(NOTNULLCHAR,Types.CHAR,"charNotNull");
		tbDef.addMapColumn(DFLTBLOB,Types.BLOB,"blobCol");
		tbDef.addMapColumn(DFLTCLOB,Types.CLOB,"clobCol");
		tbDef.addMapColumn(NOTNULLBOOLEAN,Types.BOOLEAN,"booleanNotNull");
		tbDef.addMapColumn(NOTNULLBOOL,Types.BOOLEAN,"boolNotNull");
		tbDef.addMapColumn(NOTNULLBIT,Types.BIT,"bitNotNull");
		tbDef.addMapColumn(DFLTBIGINT,Types.BIGINT,"bigIntDflt");
		tbDef.addMapColumn(DFLTINT8,Types.BIGINT,"int8Dflt");
		tbDef.addMapColumn(NOTNULLIDENTITY,Types.ROWID,"identityNotNull");
		
		return tbDef;
	}

}
