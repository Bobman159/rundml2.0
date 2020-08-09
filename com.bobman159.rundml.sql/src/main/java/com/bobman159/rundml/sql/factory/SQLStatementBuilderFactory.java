package com.bobman159.rundml.sql.factory;

import com.bobman159.rundml.sql.builders.impl.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.h2.H2SelectStatementBuilder;
import com.bobman159.rundml.sql.mysql.MySQLSelectStatementBuilder;

/**
 * Factory class for the creation of SQL Statement builder classes.
 * 
 *
 */
public class SQLStatementBuilderFactory {
	
	private static SQLStatementBuilderFactory self = null;
	
	/**
	 *
	 * @return instance of this Factory
	 */
	public static SQLStatementBuilderFactory getInstance() {
		if (self == null) {
			self = new SQLStatementBuilderFactory();
		}
		return self;
	}
	
	private SQLStatementBuilderFactory() {
		//Make Sonar Lint happy
	}
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms.
	 * @see com.bobman159.rundml.sql.builders.impl.BaseSelectStatementBuilder
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

}
