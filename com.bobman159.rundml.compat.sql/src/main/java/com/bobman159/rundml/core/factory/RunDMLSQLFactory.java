package com.bobman159.rundml.core.factory;

import java.sql.Connection;

import com.bobman159.rundml.core.select.builder.BaseSelectStatementBuilder;
import com.bobman159.rundml.jdbc.pool.IConnectionProvider;

/**
 * Factory class for the creation of SQL Statement builder classes.
 * 
 *
 */
public class RunDMLSQLFactory {
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms.
	 * @see com.bobman159.rundml.core.select.builder.BaseSelectStatementBuilder
	 * @param conn a JDBC connection to a database
	 * @return a basic SELECT statement builder and executor
	 */
	@SuppressWarnings("rawtypes")
	public static BaseSelectStatementBuilder createBaseSelectStatement(Connection conn) {
		return new BaseSelectStatementBuilder(conn);
	}
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms
	 * @see com.bobman159.rundml.core.select.builder.BaseSelectStatementBuilder
	 * @param provider a connection pool provider
	 * @return a basic SELECT statement builder and executor
	 */
	@SuppressWarnings("rawtypes")
	public static BaseSelectStatementBuilder createBaseSelectStatement(IConnectionProvider provider) {
		return new BaseSelectStatementBuilder(provider);
	}
	
	//TODO: Add factory methods for H2 and MYSQL SELECT statement builders.
}
