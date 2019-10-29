package com.bobman159.rundml.core.sql;

import java.sql.Connection;
import java.util.List;

import com.bobman159.rundml.jdbc.pool.IConnectionProvider;
import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * Define the basic implementation for an SQL SELECT statement to use when using RunDML.
 *
 */
public interface ISQLSelect extends ISQLStatement {
	
	/**
	 * Execute an SQL SELECT using a database connection and return a list of rows.
	 * @param conn a valid jdbc connection to a database 
	 * @return a list of rows, if any returned by the SELECT
	 */
	public List<ITableRow> execute(Connection conn);
//	public List<ITableRow> execute(IConnectionProvider provider);

}
