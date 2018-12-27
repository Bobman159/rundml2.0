package net.bobs.own.db.rundml.jdbc.tests.mock;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import net.bobs.own.db.rundml.jdbc.pool.DefaultConnectionProvider;
import net.bobs.own.db.rundml.jdbc.pool.IConnectionProvider;

/**
 * Mock class for testing the <code>DefaultConnectionProvider</code>.  
 * The <code>HikariDataSource</code> passed to the constructor will 
 * come from the <code>PoolFactory</code> so tests for the methods 
 * in default connection provider should be sufficient.
 * 
 * The mocked class is created to provide methods to test the total 
 * connections, active connections and idle connections for 
 * the data source.
 * 
 * @author Robert Anderson
 *
 */
public class MockDefaultConnectionProvider extends DefaultConnectionProvider implements IConnectionProvider {

	HikariPoolMXBean poolBean = null;
	HikariDataSource source = null;
	
	public MockDefaultConnectionProvider(HikariDataSource source) {
		super(source);
		this.source = source;
		poolBean = source.getHikariPoolMXBean();
	}
	
	
	/**
	 * @return - number of active data source connections
	 */
	public int getActiveConnections() {
		return poolBean.getActiveConnections();
	}
	
	/**
	 * 
	 * @return -  number of total connections
	 */
	public int getTotalConnections() {
		return poolBean.getTotalConnections();
	}
	
	/**
	 * 
	 * @return -  number of idle connections
	 */
	public int getIdleConnections() {
		return poolBean.getIdleConnections();
	}
	
	/**
	 * 
	 * @return - the pool name for the data source
	 */
	public String getPoolName() {
		return this.source.getPoolName();
	}
	

}
