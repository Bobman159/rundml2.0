package com.bobman159.rundml.jdbc.tests.datasource;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.pool.IConnectionProvider;
import com.bobman159.rundml.jdbc.pool.PoolFactory;

class PoolFactoryTest {
	private Logger logger = LogManager.getLogger(PoolFactoryTest.class);
	private static final String PROVIDER_NULL = "provider is NULL";
	private static final String USER_DIR = "user.dir";

	@BeforeAll
	static void setUpBeforeClass() {
		//no set up needed at this time
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

	@Test
	void testMakeH2DataSource() {

	    String dir = System.getProperty(USER_DIR);
	    String dbPath = dir + "\\db\\" + "h2ds_db";
	    logger.info("dbPath: " + dbPath);
		IConnectionProvider provider = PoolFactory.makeH2DataSource(dbPath,null, null,"10"); 
	    Assertions.assertNotNull(provider,PROVIDER_NULL);
	    
	    dbPath = dbPath + ".h2.db";
	    File dbFile = new File (dbPath);
	    logger.info("dbPath: " + dbPath);
	    if (!dbFile.exists()||
	    	dbFile.isDirectory()) {
	    	fail(dbPath + " does not exist or is not a directory");
	    }

	    String dbPath2 = dir + "\\db\\" + "h2ds_db2";
		IConnectionProvider provider2 = PoolFactory.makeH2DataSource(dbPath2,"testbUser", "Abcd1234","10");
	    Assertions.assertNotNull(provider2,"provider2 is NULL");
	    dbPath2 = dbPath2 + ".h2.db";
	    File dbFile2 = new File (dbPath2);
	    if (!dbFile2.exists()||
	    	dbFile2.isDirectory()) {
	    	fail(dbPath2 + " does not exist or is not a directory");
	    }
	    
	}

	@Test
	void testMakeMySQLDataSource() {
		/* Change this to a URL that is defined in your MySQL install */
		final String TEST_URL = "//localhost/test";
		
		/* Change this to a URL that is defined in your MySQL install and has a user ID & password defined*/
		final String TEST_URL_RSSREADER = "//localhost/rssreader";
		
		IConnectionProvider provider = PoolFactory.makeMySQLDataSource(TEST_URL,null, null, "10"); 
	    Assertions.assertNotNull(provider,PROVIDER_NULL);
	    
	    
		IConnectionProvider provider2 = PoolFactory.makeMySQLDataSource(TEST_URL_RSSREADER,"rssuser", "Ghum8546", "10"); 
	    Assertions.assertNotNull(provider2,PROVIDER_NULL);

	}

	@Test
	void testMakeDataSourceH2() {

		String propFile = System.getProperty(USER_DIR);
		propFile = propFile +"\\HikariConfigs\\H2Config.properties";
		logger.info("path:" + propFile);
		DefaultConnectionProvider provider = PoolFactory.makeDataSource(propFile);
	    Assertions.assertNotNull(provider,PROVIDER_NULL);
	    
	}
	
	@Test
	void testMakeDataSourceMySQL() {
		/*=====================================================================
		 * IF THE TEST FAILS TO CREATE A DATASOURCE VERIFY THAT THE 
		 * WAMPMYSQLD64 Windows Service IS STARTED!
		 *=====================================================================
		 */
		String propFile = System.getProperty(USER_DIR);
		propFile = propFile +"\\HikariConfigs\\MySQLConfig.properties";
		logger.info("path:" + propFile);
		DefaultConnectionProvider provider = PoolFactory.makeDataSource(propFile);
	    Assertions.assertNotNull(provider,PROVIDER_NULL);

	}

}
