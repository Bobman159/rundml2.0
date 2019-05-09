package com.bobman159.rundml.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A connection provider interface which allows for defining a JDBC connection pool
 * that can be used with rundml to execute SQL statements.
 *
 */
public interface IConnectionProvider {

	/**
	 * Obtain a JDBC <code>Connection</code> object from the pool
	 * @return a JDBC connection
	 */
	public Connection getConnection();
	
	/**
	 * Release a JDBC <code>Connection</code> back to the connection pool 
	 * and indicates it is available.
	 * @param conn the <code>Connection</code> to be released
	 * @throws SQLException if an error occurs releasing the connection
	 */
	public void releaseConnection(Connection conn) throws SQLException;
	
	/**
	 * Closes and removes <b>all available and in use connections</b>.
	 * In use connections will issue a commit prior to closing the connection.
	 * This method should be called to prevent resource leaks from 
	 * connections which are not properly closed.
	 */
	public void closePool();
	
	/**
	 * Set indicator for whether connections pool trace information should be 
	 * logged when connections are obtained and released.  <p>Logging shows 
	 * 1) the total number of available connections, 2) the number of active connections 
	 * and 3) number of idle connections. Connection tracing is off by default.
	 * 
	 * @param trace indicator true turns connection tracing on, 
	 * false turns connection tracing off.
	 *  
	 */
	public void setPoolConnectionTrace(boolean trace);

}
