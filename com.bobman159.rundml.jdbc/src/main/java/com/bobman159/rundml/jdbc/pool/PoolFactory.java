package com.bobman159.rundml.jdbc.pool;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.jdbc.resources.Messages;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/** 
 *	A factory class for creating connection pools for runDML supported DBMS databases.
 *	HikariCP is used for the connection pooling.
 * 	
 */
public class PoolFactory {

	private static Logger logger = LogManager.getLogger(PoolFactory.class);
	
	//TODO: Add makeH2Connection method to define single H2 Connection?
	//TODO: Add makeMySQLConnection method to define single MySQL Connection?

	/**
	 * Define a connection pool using the specified information for configuration. 
	 * <b>Do not specify the file extension <code>.h2.db</code> for the dbPath parameter.</b>
	 * It will result in database not found messages in h2 1.3.176.
	 * 
	 * @param dbPath full path to the specified H2 database 
	 * @param userId the user Id for logon information, null if no userid
	 * @param password password for the user Id, null if no password
	 * @param numberConnections the number of connections to be defined in the pool
	 * 
	 * @return a <code>DefaultConnectionProvider</code> data source.
	 */
	public static DefaultConnectionProvider makeH2DataSource(String dbPath,String userId, String password, 
			 						  String numberConnections) {
	
		logger.debug(Messages.bind(Messages.propCreateConfig, "h2"));
		Properties dbProps = new Properties();
	   	dbProps.put("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource");
	   	dbProps.put("dataSource.url","jdbc:h2:" + dbPath);
	   	if (userId != null) {
		   	dbProps.put("dataSource.user",userId);
	   	}

	   	if (password != null) {
	   		dbProps.put("dataSource.password",password);
	   	}
	   	dbProps.put("maximumPoolSize",numberConnections);
		HikariConfig config = new HikariConfig(dbProps);
		HikariDataSource ds = new HikariDataSource(config);
		
		DefaultConnectionProvider provider = new DefaultConnectionProvider(ds);
		logger.debug(Messages.bind(Messages.dataSourceAdded, ds.getPoolName()));				

		return provider;

	}

	/**
	 * Define a MySQL connection pool using the specified information for configuration. 
	 * 
	 * @param dbUrl url for MySql database
	 * @param userId the user Id for logon information, null if no userid
	 * @param password password for the user Id, null if no password
	 * @param numberConnections the number of connections to be defined in the pool
	 * 
 	 * @return a <code>DefaultConnectionProvider</code> interface for the data source.
	 */

	public static DefaultConnectionProvider makeMySQLDataSource(String dbUrl,String userId, String password, 
			  								 String numberConnections) {


			Properties dbProps = new Properties();
			dbProps.put("jdbcUrl","jdbc:mysql:" + dbUrl);
			if (userId != null) {
				dbProps.put("dataSource.user", userId);
			}
			if (password != null) {
				dbProps.put("dataSource.password", password);

			}
			dbProps.put("maximumPoolSize", numberConnections);
			HikariConfig config = new HikariConfig(dbProps);
			HikariDataSource ds = new HikariDataSource(config);

			DefaultConnectionProvider provider = new DefaultConnectionProvider(ds);
			logger.debug(Messages.bind(Messages.propCreateConfig, "MySQL"));
			logger.debug(Messages.bind(Messages.dataSourceAdded, ds.getPoolName()));
			
			return provider;
	}
	 
	 /**
	  * Create a connection pool using a .properties file with the necessary information for defining the pool.
	  * 
	  * @param propFile full path to .properties file.
	  * 
	  * For .properties file parameters, see https://github.com/brettwooldridge/HikariCP#configuration.
	  * 	  
 	  * @return a <code>DefaultConnectionProvider</code> interface for the data source. 	  
 	  * 
	  */
	public static DefaultConnectionProvider makeDataSource(String propFile)  {
		
		logger.debug(Messages.bind(Messages.initPoolMessage, "properties"));
		HikariConfig config = new HikariConfig(propFile);
		HikariDataSource ds = new HikariDataSource(config);
		DefaultConnectionProvider provider = new DefaultConnectionProvider(ds);
		logger.debug(Messages.bind(Messages.dataSourceAdded,ds.getPoolName()));
		
		return provider;
	}

	private PoolFactory() {
		throw new IllegalStateException("PoolFactory should not be created.");
	}
}
