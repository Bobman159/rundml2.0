package com.bobman159.rundml.mysql.select.steps;

import java.util.List;

import com.bobman159.rundml.jdbc.select.ITableRow;

/**
 * Final step in the SELECT build process.  Executes a SELECT statement for a 
 * table and returns a number of rows.
 * 
 */
public interface MySQLSelectFetchStep {
	
	/**
	 * Returns a list of the results from the executed query
	 * @return a list of SELECT results
	 */
	public List<ITableRow> fetch();
}
