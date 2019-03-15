package com.bobman159.rundml.jdbc.tests.datasource;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

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

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
	void testMakeH2DataSource() {

	    String dir = System.getProperty("user.dir");
	    String dbPath = dir + "\\db\\" + "h2ds_db";
	    System.out.println("dbPath: " + dbPath);
		IConnectionProvider provider = PoolFactory.makeH2DataSource(dbPath,null, null,"10"); 
	    Assertions.assertNotNull(provider,"provider is NULL");
	    
	    dbPath = dbPath + ".h2.db";
	    File dbFile = new File (dbPath);
	    System.out.println("dbPath: " + dbPath);
	    if (dbFile.exists() == false ||
	    	dbFile.isDirectory() == true) {
	    	fail(dbPath + " does not exist or is not a directory");
	    }

	    String dbPath2 = dir + "\\db\\" + "h2ds_db2";
		IConnectionProvider provider2 = PoolFactory.makeH2DataSource(dbPath2,"testbUser", "Abcd1234","10");
	    Assertions.assertNotNull(provider2,"provider2 is NULL");
	    dbPath2 = dbPath2 + ".h2.db";
	    File dbFile2 = new File (dbPath2);
	    if (dbFile2.exists() == false ||
	    	dbFile2.isDirectory() == true) {
	    	fail(dbPath2 + " does not exist or is not a directory");
	    }
	    
	}

	@Test
	void testMakeMySQLDataSource() {
		/* Change this to a URL that is defined in your MySQL install */
		final String TEST_URL = "//localhost/test";
		
		/* Change this to a URL that is defined in your MySQL install and has a user ID & password defined*/
		final String TEST_URL_PASSWORD = "//localhost/rssreader";
		
		IConnectionProvider provider = PoolFactory.makeMySQLDataSource(TEST_URL,null, null, "10"); 
	    Assertions.assertNotNull(provider,"provider is NULL");
	    
	    
		IConnectionProvider provider2 = PoolFactory.makeMySQLDataSource(TEST_URL_PASSWORD,"rssuser", "Ghum8546", "10"); 
	    Assertions.assertNotNull(provider2,"provider is NULL");

	}

	@Test
	void testMakeDataSourceH2() {

		String propFile = System.getProperty("user.dir");
		propFile = propFile +"\\HikariConfigs\\H2Config.properties";
		System.out.println("path:" + propFile);
		DefaultConnectionProvider provider = (DefaultConnectionProvider) PoolFactory.makeDataSource(propFile);
	    Assertions.assertNotNull(provider,"provider is NULL");
	    
	}
	
	@Test
	void testMakeDataSourceMySQL() {
		/*=====================================================================
		 * IF THE TEST FAILS TO CREATE A DATASOURCE VERIFY THAT THE 
		 * WAMPMYSQLD64 Windows Service IS STARTED!
		 *=====================================================================
		 */
		String propFile = System.getProperty("user.dir");
		propFile = propFile +"\\HikariConfigs\\MySQLConfig.properties";
		System.out.println("path:" + propFile);
		DefaultConnectionProvider provider = (DefaultConnectionProvider) PoolFactory.makeDataSource(propFile);
	    Assertions.assertNotNull(provider,"provider is NULL");

	}

}
