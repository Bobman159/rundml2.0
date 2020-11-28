package com.bobman159.rundml.jdbc.select.execution;

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

import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.integration.jdbc.mocks.TypeTest;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionPool;
import com.bobman159.rundml.jdbc.pool.PoolFactory;
import com.bobman159.rundml.jdbc.utils.tests.SQLModelTestFactory;

/**
 * Test RunDMLExecutor class, since the executor calls are the same (or should be) when using H2 or 
 * MySQL, only H2 database testing is done.
 *
 */
class RunDMLExecutorTests {
	
	private static SQLStatementModel selectModel;
	private static DefaultConnectionPool h2Provider;
	/**
	 * H2 User Id, change this value if you want to use a different user Id
	 */
	private static final String H2USER = "DmlTestUser";
	
	/**
	 * H2 User Id password, change this value if you want to use a different password
	 */
	private static final String H2PASSWORD = "Tgbn6929";
		
	private static final String NUMBER_CONNECTIONS = "5";
	private static Logger logger = LogManager.getLogger(RunDMLExecutorTests.class.getName());

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
	void testGetInstance() {
		RunDMLExecutor executor = RunDMLExecutor.getInstance();
		Assertions.assertNotNull(executor);
	}

	@Test
	void testExecuteSelect() {

		RunDMLExecutor executor = RunDMLExecutor.getInstance();
		
		List<Object> results = new ArrayList<>();
		//Should cause an SQLException on the Prepare of the SQL statment....
		selectModel = SQLModelTestFactory.getInstance().createUndefinedColumnSelectModel();
		results = executor.executeSelect(h2Provider.getConnection(), selectModel, TypeTest.class);
		Assertions.assertEquals(0, results.size());
		
		//Now try executing another SELECT to verify the service is restarted correctly.
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		results = executor.executeSelect(h2Provider.getConnection(), selectModel, TypeTest.class);
		Assertions.assertEquals(6, results.size());

	}

	@Test
	void testShutdown() {
		RunDMLExecutor executor = RunDMLExecutor.getInstance();
		executor.shutdown();
		
		List<Object> results = new ArrayList<>();
		//Try executing a statement after shutdown, the service should be restarted
		selectModel = SQLModelTestFactory.getInstance().createH2SelectTypeTestModel();
		results = executor.executeSelect(h2Provider.getConnection(), selectModel, TypeTest.class);
		Assertions.assertEquals(6, results.size());
	}

}
