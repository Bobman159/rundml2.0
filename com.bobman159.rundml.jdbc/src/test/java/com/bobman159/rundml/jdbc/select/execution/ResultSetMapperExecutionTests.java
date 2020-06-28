package com.bobman159.rundml.jdbc.select.execution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.expressions.IExpressionFactory;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;
import com.bobman159.rundml.core.sql.impl.SQLClauses;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;
import com.bobman159.rundml.jdbc.utils.tests.SQLModelTestFactory;
import com.bobman159.rundml.sql.h2.mocktables.H2IllegalAccessExceptionMock;
import com.bobman159.rundml.sql.h2.mocktables.H2InstantiationExceptionMock;
import com.bobman159.rundml.sql.h2.mocktables.H2MockPrimitivesTypeTest;
import com.bobman159.rundml.sql.h2.mocktables.H2MockStringTypeTest;
import com.bobman159.rundml.sql.h2.mocktables.H2NoClassFieldExceptionMock;
import com.bobman159.rundml.sql.h2.mocktables.TypeTest;

public class ResultSetMapperExecutionTests {

	private static SQLStatementModel selectModel;
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
	private static Logger logger = LogManager.getLogger(ResultSetMapperExecutionTests.class.getName());
	


	@BeforeAll
	static void setUpBeforeClass() {
		
		String dir = System.getProperty("user.dir");
		String dbPath = dir + "\\H2Database\\" + "RundmlTest";
		logger.info("dbPath: " + dbPath);
		h2Provider = PoolFactory.makeH2DataSource(dbPath, H2USER, H2PASSWORD, 
				NUMBER_CONNECTIONS);
		
		CoreUtils.initRunDML();
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
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
	 *	*	No alternate field names specified (IFieldMap not implemented)
	 *	*	Should use class TypeTest.java 
	 *
	 */
	@Test
	void allColumnTypesObjectTest() {

		logger.info("****** allColumnTypesObjectTest ******");
		
		List<Object> results = RunDMLExecutor.getInstance()
											 .executeSelect(h2Provider.getConnection(), selectModel, TypeTest.class);		
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
	 *	*	Use H2MockPrimitivesTypeTest 
	 *	*	Implements IFieldMap and only some field names specified as alternates
	 */
	@Test
	void allColumnTypesPrimitivesTest() {

		logger.info("****** allColumnTypesPrimitivesTest ******");

		List<Object> results = new ArrayList<Object>();

		results = RunDMLExecutor.getInstance()
								.executeSelect(h2Provider.getConnection(), selectModel, H2MockPrimitivesTypeTest.class);		
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
	 *	*	Uses H2MockStringTypeTest.
	 *	*	Implements IFieldMap and all fields specified as alternates
	 *
     */
	@Test
	void allColumnTypesStringTest() {
		
		logger.info("****** allColumnTypesStringTest ******");
		
		selectModel.addExpressionList(SQLClauses.SQLClause.SELECTEXPR, IExpressionFactory.column("NotNullIdentity"));
		List<Object> results = RunDMLExecutor.getInstance()
											 .executeSelect(h2Provider.getConnection(), selectModel,
													 		H2MockStringTypeTest.class);		
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
		Assertions.assertEquals("3",test3.getIdentityNotNull());
		
		Assertions.assertEquals("000186a3",test3.getBinaryNotNull());
		Assertions.assertEquals("000186a3",test3.getVarBinaryDflt()); 
		
	}
	
	/*
	 *	*	Uses H2MockStringTypeTest.
	 *	*	Implements IFieldMap and all fields specified as alternates
	 *
     */
	@Test
	void mappingClassAllFieldsTest() {
		
		logger.info("****** mappingClassAllFieldsTest ******");
		
		List<Object> results = RunDMLExecutor.getInstance()
											 .executeSelect(h2Provider.getConnection(), selectModel, 
													 		H2MockStringTypeTest.class);
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
	
	@Test
	void resultSetMapperExceptionsTest() {
		
		final String ERROR_MSG = "RunDML encountered a SQL Exception error during execution";
		final String ILLEGAL_ACCESS_ERROR = "RunDML encountered a Table Row Class Reflection "  +
			"error in class com.bobman159.rundml.sql.h2.mocktables." + 
			"H2InstantiationExceptionMock";
		final int[] dfltSignedValues = new int[] {100000,100001,100002,100003,100004};
		
		logger.info("****** resultSetMapperExceptionsTest ******");
		logger.info("****** mapResultRow method exceptions: ******");
		
		/* SQLException Error - The result set is already closed (by createResultSet) */
		SQLStatementModel sqlExModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		String stmtSQLEx = SQLStatementSerializer.serializeSelect(sqlExModel);
		ResultSet rsSqlEx = null;
		Connection conn = h2Provider.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(stmtSQLEx)) {
			rsSqlEx = ps.executeQuery(); 
			ResultSetMapper mapper = new ResultSetMapper(TypeTest.class);
			//This will force an SQLException because the ResultSet is closed
			rsSqlEx.close();	
			mapper.mapResultRow(rsSqlEx);
		} catch (SQLException sqlex) {	
			logger.error("Exception creating result set",sqlex);
		} catch (RunDMLException e) {
			Assertions.assertEquals(RunDMLException.SQL_ERROR, 
									e.getExecutionPhase());
			Assertions.assertEquals(ERROR_MSG, e.getRunDMLMessage());
			logger.error("Exception",e);
		} catch (NoTableRowClassFieldException ntrcfex) {
			logger.error("No Table Row Class Field Exception: ",ntrcfex);
		} finally {
			try {
				conn.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}
		
		/* IllegalAccesException - Use private class constructor */
		SQLStatementModel instExModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		String stmtInstEx = SQLStatementSerializer.serializeSelect(instExModel);
		ResultSet rsInstEx = null;
		Connection connInstEx = h2Provider.getConnection();
		try (PreparedStatement ps = connInstEx.prepareStatement(stmtInstEx)) {
			rsInstEx = ps.executeQuery(); 
			ResultSetMapper mapper = new ResultSetMapper(H2InstantiationExceptionMock.class);
			while (rsInstEx.next()) {
				mapper.mapResultRow(rsInstEx);
			}
		} catch (SQLException sqlex) {	
			logger.error("Exception creating result set",sqlex);
		} catch (NoTableRowClassFieldException ntrcfex) {
			logger.error("No Table Row Class Field Exception: ",ntrcfex);	
		} catch (RunDMLException e) {
			Assertions.assertEquals(RunDMLException.TABLE_ROW_CLASS_REFLECTION, 
									e.getExecutionPhase());
			Assertions.assertEquals(ILLEGAL_ACCESS_ERROR, e.getRunDMLMessage());
			logger.error("Exception",e);
		} finally {
			try {
				connInstEx.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}		
		
		/* NoTableRowClassFieldException -  */
		SQLStatementModel noFieldExModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		String stmtNoFieldEx = SQLStatementSerializer.serializeSelect(noFieldExModel);
		ResultSet rsNoFieldEx = null;
		Connection connNoFieldEx = h2Provider.getConnection();
		try (PreparedStatement ps = connNoFieldEx.prepareStatement(stmtNoFieldEx)) {
			rsNoFieldEx = ps.executeQuery(); 
			ResultSetMapper mapper = new ResultSetMapper(H2NoClassFieldExceptionMock.class);
			while (rsNoFieldEx.next()) {
				mapper.mapResultRow(rsNoFieldEx);
			}
		} catch (SQLException sqlex) {	
			logger.error("Exception creating result set",sqlex);
		} catch (NoTableRowClassFieldException ntrcfex) {
			logger.error("No Table Row Class Field Exception: ",ntrcfex);	
		} catch (RunDMLException e) {
			Assertions.assertEquals(RunDMLException.TABLE_ROW_CLASS_REFLECTION, 
									e.getExecutionPhase());
			Assertions.assertEquals(ILLEGAL_ACCESS_ERROR, e.getRunDMLMessage());
			logger.error("Exception",e);
		} finally {
			try {
				connInstEx.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}		

		
		logger.info("****** mapColumnToField method exceptions: ******");
		/* IllegalAccesException - Use final on class field */
		SQLStatementModel illAccExModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		String stmtIllAccEx = SQLStatementSerializer.serializeSelect(illAccExModel);
		ResultSet rsIllAccEx = null;
		Connection connIllAccEx = h2Provider.getConnection();
		try (PreparedStatement ps = connIllAccEx.prepareStatement(stmtIllAccEx)) {
			rsIllAccEx = ps.executeQuery(); 
			ResultSetMapper mapper = new ResultSetMapper(H2IllegalAccessExceptionMock.class);
			int ix = 0;
			while (rsIllAccEx.next()) {
				H2IllegalAccessExceptionMock mock = (H2IllegalAccessExceptionMock) mapper.mapResultRow(rsIllAccEx);
				Assertions.assertEquals(-1, mock.getDfltInteger());
				if (ix < dfltSignedValues.length) {
					Assertions.assertEquals(dfltSignedValues[ix],mock.getDfltSigned());
				}
				ix++;
			}
			
		} catch (SQLException sqlex) {	
			logger.error("Exception creating result set",sqlex);
		} catch (NoTableRowClassFieldException ntrcfex) {
			logger.error("No Table Row Class Field Exception: ",ntrcfex);	
		} catch (RunDMLException e) {
			Assertions.assertEquals(RunDMLException.TABLE_ROW_CLASS_REFLECTION, 
									e.getExecutionPhase());
			Assertions.assertEquals(ILLEGAL_ACCESS_ERROR, e.getRunDMLMessage());
			logger.error("Exception",e);
		} finally {
			try {
				connIllAccEx.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}
	}
	
	@Test
	void tableRowClassGetNameTest() {
				
		logger.info("****** tableRowClassGetNameTest ******");
		
		SQLStatementModel trcModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		String stmtTrc = SQLStatementSerializer.serializeSelect(trcModel);
		ResultSet rsTrc = null;
		Connection conn = h2Provider.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(stmtTrc)) {
			rsTrc = ps.executeQuery(); 
			ResultSetMapper mapper = new ResultSetMapper(TypeTest.class);
			String trcClassName = mapper.getTableRowClassName();
			Assertions.assertEquals("com.bobman159.rundml.sql.h2.mocktables.TypeTest",trcClassName); 
		} catch (SQLException sqlex) {	
			logger.error("Exception creating result set",sqlex);
		} finally {
			try {
				conn.close();
			} catch (SQLException sqlex) {
				RunDMLException.createRunDMLException(sqlex, RunDMLException.SQL_ERROR, null);
			}
		}
	}
	
	/*
	 * 	Remaining Tests:
	 * 
	 *	*	All database types for parameter markers
	 *
	 *	*	Expressions in SELECT list (string, numeric) - need to support AS for mapping?
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
