package com.bobman159.rundml.sql.builders;

import java.sql.Connection;
import java.util.List;

/**
 * Define the basic implementation for an SQL SELECT statement to use when using RunDML.
 *
 */
public interface ISelectBuilder extends IStatementBuilder {
	
	/**
	 * Execute an SQL SELECT using a database connection and return a list of rows.
	 * @param conn a valid jdbc connection to a database 
	 * @param clazz class object to contain 1 row from the SELECT results
	 * @return a list of rows, if any returned by the SELECT
	 */
	public List<Object> execute(Connection conn,Class<?> clazz);

}
