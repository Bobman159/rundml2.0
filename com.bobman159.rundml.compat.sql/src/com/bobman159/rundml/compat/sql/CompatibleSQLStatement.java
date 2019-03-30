package com.bobman159.rundml.compat.sql;

import java.sql.Connection;

import com.bobman159.rundml.compat.select.builder.CompatibleSelectStatement;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

/**
 * Factory class to create compatible SQL statement builders. 
 * SQL statements built using the compatible SQL statement builders <b>should
 * </b> execute successfully on different DBMS platforms.  
 * 
 * No guarantee is made that the compatible SQL statement builders are ANSI SQL compliant or 
 * that compliant SQL statements will execute successfully on all DBMS platforms.
 *
 */
public class CompatibleSQLStatement {
	
	private CompatibleSQLStatement() {
		//Private constructor
	}
	
	/**
	 * Create the builder for a compatible SELECT statement using an existing 
	 * <code>Connection</code> object.
	 * @param - a JDBC connection
	 * @return - compatible SELECT statement builder
	 */
	public static CompatibleSelectStatement selectStatement(Connection conn) {
		return new CompatibleSelectStatement(conn);
	}
	
	/**
	 * Create the builder for a compatible SELECT statement using an existing 
	 * <code>DefaultConnectionProvider</code> for a connection pool.
	 * @param - a JDBC connection pool
	 * @return - compatible SELECT statement builder.
	 */
	public static CompatibleSelectStatement selectStatement(DefaultConnectionProvider provider) {
		return new CompatibleSelectStatement(provider);
	}
	
}
