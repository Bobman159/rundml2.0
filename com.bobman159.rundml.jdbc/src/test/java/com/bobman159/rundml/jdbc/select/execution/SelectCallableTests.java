package com.bobman159.rundml.jdbc.select.execution;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.predicates.impl.PredicateBuilder;
import com.bobman159.rundml.core.sql.SQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.SQLClauses.SQLClause;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;
import com.bobman159.rundml.jdbc.utils.tests.SQLModelTestFactory;
import com.bobman159.rundml.sql.h2.mocktables.H2NoClassFieldExceptionMock;
import com.bobman159.rundml.sql.h2.mocktables.TypeTest;

class SelectCallableTests {

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
	private static Logger logger = LogManager.getLogger(SelectCallable.class.getName());
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSelectCallable() {
		logger.info("****** testSelectCallable ******");
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		SelectCallable callable = new SelectCallable(h2Provider.getConnection(), selectModel, TypeTest.class);
		Assertions.assertNotNull(callable);
	}

	@Test
	void testCall() {
		
		logger.info("****** testCall ******");
		/* Execution returns 0 rows -> Successful Execution, NO Exceptions */
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		SQLStatementModel model = new SQLStatementModel();
		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column("DfltInteger"));
		model.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column("NotNullMediumInt"));
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName("rundml", "typeTest"));
		PredicateBuilder pred = PredicateBuilder.where(SQLTypeFactory.column("DfltInteger")).isEqual(999999)
								  .build();
		model.addClause(SQLClause.WHERE, pred);
		Connection conn = h2Provider.getConnection();
		SelectCallable callable = new SelectCallable(conn, model, TypeTest.class);
		List<Object> results = null;
		try {
			results = callable.call();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (RunDMLException e) {
			logger.error(e.getMessage(), e);
		}
		Assertions.assertEquals(0, results.size());
		
		/* Execution returns 6 rows -> Successful Execution */
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		Connection resultsConn = h2Provider.getConnection();
		SelectCallable resultsCallable = new SelectCallable(resultsConn, selectModel, TypeTest.class);
		List<Object> resultsRows = null;
		try {
			resultsRows = resultsCallable.call();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (RunDMLException e) {
			logger.error(e.getMessage(), e);
		}
		Assertions.assertEquals(6, resultsRows.size());
		
		//TODO: Add test(s) for SQLException 
		/* NOTE: Should test for SQLException once UPDATE support is added 
		 * 		 Executing an UPDATE SQL statement by using SelectCallable.call would cause an SQLException in
		 * 		the executeQuery method.  See testcreateQueryResults for an example
		 */
		
		/* NoTableRowClassFieldException - Testing Exception path in call method */
		SQLStatementModel ntrcfexModel = new SQLStatementModel();
		ntrcfexModel.addClause(SQLClause.SELECT);
		ntrcfexModel.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column("DfltInteger"));
		ntrcfexModel.addExpressionList(SQLClause.SELECTEXPR, SQLTypeFactory.column("NotNullMediumInt"));
		ntrcfexModel.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName("rundml", "typeTest"));
		Connection ntrcfexConn = h2Provider.getConnection();
		SelectCallable ntrcfexCallable = new SelectCallable(ntrcfexConn, ntrcfexModel, 
				H2NoClassFieldExceptionMock.class);
		List<Object> ntrcfexResults = null;
		try {
			ntrcfexResults = ntrcfexCallable.call();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (RunDMLException e) {
			logger.error(e.getMessage(), e);
		}
		Assertions.assertNull(ntrcfexResults);
				

	}
	
	@Test
	void testcreateQueryResults() {
		
		logger.info("****** testcreateQueryResults ******");
		final String SQLEX_ERROR = "org.h2.jdbc.JdbcSQLNonTransientException: Method is only allowed for a query. Use execute or executeUpdate instead of executeQuery; SQL statement:\n" + 
				"Update rundml.typeTest set dfltInteger = 999999 [90002-199]";

		/* Test SQL Exception in createQueryResults - Execute UPDATE statement using executeQuery method 
		 * 	invokecreateQueryResults calls the createQueryResultsMethod using java reflection
		 */	
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		try {
			invokecreateQueryResults(selectModel,h2Provider.getConnection(),
				"Update rundml.typeTest set dfltInteger = 999999");
		} catch (RunDMLException rdex) {
			Assertions.assertEquals(RunDMLException.SQL_ERROR, rdex.getExecutionPhase());
			Assertions.assertEquals(SQLEX_ERROR, rdex.getMessage());
			logger.error(rdex.getMessage(), rdex);
		}
		
		/* Test SQL Exception in createQueryResults - Close connection
		 * 	invokecreateQueryResults calls the createQueryResultsMethod using java reflection
		 */	
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		try {
			Connection conn = h2Provider.getConnection();
			conn.close();
			invokecreateQueryResults(selectModel,conn,
				"Update rundml.typeTest set dfltInteger = 999999");
		} catch (SQLException sqlex) {
			logger.error(sqlex.getMessage(),sqlex);
		} catch (RunDMLException rdex) {
			Assertions.assertEquals(RunDMLException.SQL_ERROR, rdex.getExecutionPhase());
			Assertions.assertEquals(SQLEX_ERROR, rdex.getMessage());
			logger.error(rdex.getMessage(), rdex);
		}
	}

	private static void invokecreateQueryResults(SQLStatementModel model,
			Connection conn,String sqlText) throws RunDMLException {

		try {
			Connection connSqlEx = h2Provider.getConnection();
			PreparedStatement sqlExPs = connSqlEx.prepareStatement(sqlText);
			
			SelectCallable callableSqlEx = new SelectCallable(connSqlEx, model, TypeTest.class);
			Class<? extends SelectCallable> clazz = callableSqlEx.getClass(); 
			Class<? extends PreparedStatement> psClazz = (Class<? extends PreparedStatement>) Class.forName("java.sql.PreparedStatement");
			Method callMethod = clazz.getDeclaredMethod("createQueryResults", psClazz);
			callMethod.setAccessible(true);
			callMethod.invoke(callableSqlEx, sqlExPs);
		} catch (SQLException sqlex) {
			logger.error(sqlex.getMessage(), sqlex);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException |
				 InvocationTargetException | ClassNotFoundException e1 ) {
			logger.error(e1.getMessage(), e1);
			if (e1.getCause() instanceof RunDMLException) {
				RunDMLException rdex = (RunDMLException) e1.getCause();
				throw rdex;
			}
			e1.printStackTrace();
		}
	}
}
