package com.bobman159.rundml.mysql;

import java.sql.Connection;

import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.mysql.select.builder.MySQLSelectStatement;

/**
 * Factory class to create MySQL DBMS SQL statement builders.
 *
 */
public class MySQLSQLStatement {

	private MySQLSQLStatement() {
		//make sonar lint happy
	}
	
	/**
	 * Create the builder for an MySQL DBMS SELECT statement using an existing 
	 * <code>Connection</code> object
	 * @param conn a JDBC connection
	 * @return MySQL SELECT statement builder
	 */
	public static MySQLSelectStatement selectStatement(Connection conn) {
		return new MySQLSelectStatement(conn);
	}
	
	/**
	 * Create the builder for a MySQL DBMS SELECT statement using an existing
	 * <code>DefaultConnectionProvider</code> for a connection pool.
	 * @param provider a JDBC connection pool
	 * @return MySQL SELECT statement builder
	 */
	public static MySQLSelectStatement selectStatement(DefaultConnectionProvider provider) {
		return new MySQLSelectStatement(provider);
	}
}
