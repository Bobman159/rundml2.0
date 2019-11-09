package com.bobman159.rundml.sql.factory;

import com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.h2.H2SelectStatementBuilder;
import com.bobman159.rundml.sql.mysql.MySQLSelectStatementBuilder;

/**
 * Factory class for the creation of SQL Statement builder classes.
 * 
 *
 */
public class RunDMLSQLFactory {
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms.
	 * @see com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder
	 * @return a basic SELECT statement builder and executor
	 */
	@SuppressWarnings("rawtypes")
	public static BaseSelectStatementBuilder createBaseSelectStatement() {
		return new BaseSelectStatementBuilder();
	}
	
	/**
	 * Create the builder for an MySQL DBMS SELECT statement.
	 * @return MySQL SELECT statement builder and executor
	 */
	public static MySQLSelectStatementBuilder createMySQLSelectStatement() {
		return new MySQLSelectStatementBuilder();
	}
	
	/**
	 * Create the builder for an H2 DBMS SELECT statement
	 * @return H2 SELECT statement builder
	 */
	public static H2SelectStatementBuilder createH2SelectStatement() {
		return new H2SelectStatementBuilder();
	}
	
	
	//TODO: Add factory methods for H2 SELECT statement builders.
}
