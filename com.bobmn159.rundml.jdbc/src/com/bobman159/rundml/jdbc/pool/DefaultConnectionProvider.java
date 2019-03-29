package com.bobman159.rundml.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

/**
 * Default implementation of IConnectionProvider for the purpose of obtaining, releasing 
 * and the closing of JDBC connections and the connection pool itself.  One instance of 
 * DefaultConnectionProvider is needed for each instance of a defined connection pool.
 * 
 * @see com.bobman159.rundml.jdbc.pool.PoolFactory
 * @see com.bobman159.rundml.jdbc.pool.IConnectionProvider
 * @author Robert Anderson
 *
 */
public class DefaultConnectionProvider implements IConnectionProvider {

	private boolean poolTracing = false;
	private HikariDataSource dataSource = null;
	private Logger logger = LogManager.getLogger(DefaultConnectionProvider.class);
	
	/**
	 * Creates an instance of a default connection provider for JDBC connections.
	 * @param source - DataSource object which represents a HikariCP Connection pool.
	 */
	public DefaultConnectionProvider(HikariDataSource source) {
		dataSource = source;
	}
	
	/**
	 * @see com.bobman159.rundml.jdbc.pool.IConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {

		Connection conn = null;
		final String DEBUG_STATUS="PoolId {0} get connection total= {2} active= {2} idle= {3}";
		String dbgOut = " ";
		
		try {			
			conn = dataSource.getConnection();
			HikariPoolMXBean bean = dataSource.getHikariPoolMXBean();
			dbgOut = MessageFormat.format(DEBUG_STATUS,dataSource.getPoolName(), bean.getTotalConnections(),
					                        bean.getActiveConnections(),bean.getIdleConnections());			
			logConnectionTrace(dbgOut);
		}
		catch (SQLException sqlex) {
         logger.error(sqlex.getMessage(), sqlex);
		   javax.swing.JOptionPane.showMessageDialog(null, 
                	" Connection to database failed ! See log for errors.", "Failed Connection!",
    						javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(0);
		}
		
		return conn;
	}

	/**
	 * @see com.bobman159.rundml.jdbc.pool.IConnectionProvider#releaseConnection(Connection)
	 */
	@Override
	public synchronized void releaseConnection(Connection conn) throws SQLException {

		final String DEBUG_STATUS="PoolId {0} close connection total= {1} active= {2} idle= {3}";
		
		conn.close();
		HikariPoolMXBean bean = dataSource.getHikariPoolMXBean();
		String dbgOut = MessageFormat.format(DEBUG_STATUS,dataSource.getPoolName(),bean.getTotalConnections(),
				                        bean.getActiveConnections(),bean.getIdleConnections());			
		logConnectionTrace(dbgOut);		
		
	}

	/**
	 * @see com.bobman159.rundml.jdbc.pool.IConnectionProvider#closePool()
	 */
	@Override
	public void closePool() {
		
		dataSource.close();
		
	}
	
	/**
	 * @see com.bobman159.rundml.jdbc.pool.IConnectionProvider#setPoolConnectionTrace(boolean)
	 */
	@Override 
	public void setPoolConnectionTrace(boolean trace) {
	   this.poolTracing = trace;
	}
	
	private void logConnectionTrace(String msg) {
	   
	   if (poolTracing) {
	      logger.debug(msg);
	   }
	}

}
