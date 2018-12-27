package net.bobs.own.db.rundml.jdbc.pool;

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
 * @see net.bobs.own.db.rundml.jdbc.pool.PoolFactory
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
	 * Returns a <code>Connection</code> back to the pool, and indicates 
	 * it is available.
	 * 
	 * @param conn - the <code>Connection</code> to be returned
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
	 * Closes and removes <b>all available and in use connections</b>.
	 * In use connections will issue a commit prior to closing the connection.
	 * This method should be called to prevent resource leaks from 
	 * connections which are not properly closed.
	 */
	@Override
	public void closePool() {
		
		dataSource.close();
		
	}
	
	/**
	 * Set indicator for whether connections pool trace information should be logged when connections 
	 * are obtained and released.  Logging shows 1) the total number of available connections, 2) the
	 * number of active connections and 3) number of idle connections. Connection tracing is off by default.
	 * 
	 * @param - trace indicator true turns connection tracing on, false turns connection tracing off.
	 *  
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
