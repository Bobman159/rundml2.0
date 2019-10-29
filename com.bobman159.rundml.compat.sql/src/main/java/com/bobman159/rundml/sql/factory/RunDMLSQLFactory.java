package com.bobman159.rundml.sql.factory;

import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.mysql.MySQLSelectStatementBuilder;
import com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder;

/**
 * Factory class for the creation of SQL Statement builder classes.
 * 
 *
 */
public class RunDMLSQLFactory {
	
	/**
	 * Create a basic SELECT statement builder for execution on different DBMS platforms.
	 * @see com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder
	 * @param tbDef table mapping definition for the SELECT
	 * @return a basic SELECT statement builder and executor
	 */
	@SuppressWarnings("rawtypes")
	public static BaseSelectStatementBuilder createBaseSelectStatement(TableDefinition tbDef) {
		return new BaseSelectStatementBuilder(tbDef);
	}
	
	/**
	 * Create the builder for an MySQL DBMS SELECT statement using an existing 
	 * <code>Connection</code> object
	 * @param mapper table mapping definition for the SELECT
	 * @return MySQL SELECT statement builder
	 */
	public static MySQLSelectStatementBuilder createMySQLSelectStatement(TableDefinition mapper) {
		return new MySQLSelectStatementBuilder(mapper);
	}
	
	
	//TODO: Add factory methods for H2 SELECT statement builders.
}
