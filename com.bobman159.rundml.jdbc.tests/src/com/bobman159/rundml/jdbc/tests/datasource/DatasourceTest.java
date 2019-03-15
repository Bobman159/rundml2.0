package com.bobman159.rundml.jdbc.tests.datasource;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;

class DatasourceTest {
	static final String TEST_URL_PASSWORD = "//localhost/rssreader";

	private static DefaultConnectionProvider h2Provider;
	private static DefaultConnectionProvider mysqlProvider;
	private static Logger logger = LogManager.getLogger(DatasourceTest.class.getName());

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String dir = System.getProperty("user.dir");
		String dbPath = dir + "\\db\\" + "datasource_test";
		System.out.println("dbPath: " + dbPath);

		h2Provider = PoolFactory.makeH2DataSource(dbPath, "EzMenuUser", "Aqpk3728", "10");
		mysqlProvider = PoolFactory.makeMySQLDataSource(TEST_URL_PASSWORD, "rssuser", "Ghum8546", "10");

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

	/**
	 * Test an H2 Database Connection pool using HikariCP. This test only tests
	 * calling getConnection() = # of connections in the pool. When testing where
	 * calling getConnections() > # of connections in the pool there was a timeout
	 * message in the log and the JUnit hung, I think this is a HikariCP issue ->
	 * https://github.com/brettwooldridge/HikariCP/issues/1215
	 */
	@Test
	void testH2Provider() {

		final int POOL_INIT_SIZE = 10;

		logger.debug("Start testH2Provider");
		// Only test the IConnectionPool interface methods since HikariCp should handle
		// the Connection
		// pool details.
		Assertions.assertNotNull(h2Provider);

		// Turn tracing on
		h2Provider.setPoolConnectionTrace(true);

		// Get Some Connections
		logger.debug("Test getConnection()");
		try {

			// Test # getConnection() > initial PoolSize
			Connection[] conns = new Connection[POOL_INIT_SIZE];
			for (int count = 0; count < POOL_INIT_SIZE; count++) {
				conns[count] = h2Provider.getConnection();
				Assertions.assertNotNull(conns[count]);
				Assertions.assertEquals(false, conns[count].isClosed());
			}
			Assertions.assertEquals(POOL_INIT_SIZE, conns.length);

			// Test Release Connections
			logger.debug("Test releaseConnection()");
			for (int count = 0; count < POOL_INIT_SIZE; count++) {
				h2Provider.releaseConnection(conns[count]);
				Assertions.assertEquals(true, conns[count].isClosed());
			}

			// Test Close Pool - pool already closed
			h2Provider.closePool();

		} catch (SQLException ex) {
			fail("SQL Exception thrown!" + ex.getMessage());
		}

	}

	@Test
	void testMysqlProvider() {

		final int POOL_INIT_SIZE = 10;

		logger.debug("Start testMysqlProvider");
		// Only test the IConnectionPool interface methods since HikariCp should handle
		// the Connection
		// pool details.
		Assertions.assertNotNull(mysqlProvider);

		// Turn tracing on
		mysqlProvider.setPoolConnectionTrace(true);

		// Get Some Connections
		logger.debug("Test getConnection()");
		try {

			// Test # getConnection() > initial PoolSize
			Connection[] conns = new Connection[POOL_INIT_SIZE];
			for (int count = 0; count < POOL_INIT_SIZE; count++) {
				conns[count] = mysqlProvider.getConnection();
				Assertions.assertNotNull(conns[count]);
				Assertions.assertEquals(false, conns[count].isClosed());
			}
			Assertions.assertEquals(POOL_INIT_SIZE, conns.length);

			// Test Release Connections
			logger.debug("Test releaseConnection()");
			for (int count = 0; count < POOL_INIT_SIZE; count++) {
				mysqlProvider.releaseConnection(conns[count]);
				Assertions.assertEquals(true, conns[count].isClosed());
			}

			// Test Close Pool - pool already closed
			mysqlProvider.closePool();

		} catch (SQLException ex) {
			fail("SQL Exception thrown!" + ex.getMessage());
		}

	}

}
