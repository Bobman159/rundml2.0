package com.bobman159.rundml.jdbc.pool.tests;

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
	
	/**
	 * The MySQL database to be used for testing, this should be the database 
	 * where you ran the MySQLRunDMLInit.sql against.
	 */
	static final String MYSQL_DB = "rundml";
	
	/**
	 * The URL for the MySQL database that the MySQLRunDmlInit.sql was executed
	 */

	static final String MYSQL_DB_URL = "//localhost:3306/" + MYSQL_DB;

	private static DefaultConnectionProvider h2Provider;
	private static DefaultConnectionProvider mysqlProvider;
	private static Logger logger = LogManager.getLogger(DatasourceTest.class.getName());
	
	/**
	 * H2 User Id, change this value if you want to use a different user Id
	 */
	private static final String H2USER = "EzMenuUser";
	
	/**
	 * H2 User Id password, change this value if you want to use a different password
	 */
	private static final String H2PASSWORD = "Aqpk3728";
		
	private static final String NUMBER_CONNECTIONS = "10";
	
	/**
	 * The MySQL database user id created by MySQLRunDmlInit.sql
	 */
	private static final String MYSQL_USER = "RunDmlUser";
	
	private static final String MYSQL_PASSWORD = "Tgbn6929";

	@BeforeAll
	static void setUpBeforeClass() {
		String dir = System.getProperty("user.dir");
		logger.debug("test, message");
		String dbPath = dir + "\\db\\" + "datasource_test";
		logger.info("dbPath: " + dbPath);

		h2Provider = PoolFactory.makeH2DataSource(dbPath, H2USER, H2PASSWORD, 
				NUMBER_CONNECTIONS);
		mysqlProvider = PoolFactory.makeMySQLDataSource(MYSQL_DB_URL, MYSQL_USER, 
				MYSQL_PASSWORD, NUMBER_CONNECTIONS);

	}

	@AfterAll
	static void tearDownAfterClass() {		
		//no tear down needed at this time
	}

	@BeforeEach
	void setUp() {
		//no set up needed at this time
	}

	@AfterEach
	void tearDown() {
		//no tear down needed at this time
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
	
	@Test
	void testMysqlProviderException() {

		final String POOL_SIZE = "3";
		final int POOL_INIT_SIZE = 4;

		logger.debug("Start testMysqlProviderException");
		// Only test the IConnectionPool interface methods since HikariCp should handle
		// the Connection pool details.
		DefaultConnectionProvider mysqlProviderException = PoolFactory.makeMySQLDataSource("//localhost:3306/rundml", 
				MYSQL_USER, MYSQL_PASSWORD, POOL_SIZE);
		Assertions.assertNotNull(mysqlProviderException);

		// Turn tracing on
		mysqlProviderException.setPoolConnectionTrace(true);

		// Get Some Connections
		logger.debug("Test getConnection()");
		// Test # getConnection() > initial PoolSize
		Connection[] conns = new Connection[POOL_INIT_SIZE];
		for (int count = 0; count < POOL_INIT_SIZE; count++) {
			conns[count] = mysqlProviderException.getConnection();
			if (count < POOL_INIT_SIZE - 1) {
				Assertions.assertNotNull(conns[count]);
			} else if (count == POOL_INIT_SIZE - 1) {
				Assertions.assertNull(conns[count]);
			}
		}

		// Test Close Pool - pool already closed
		mysqlProviderException.closePool();
	}

}
