package com.bobman159.rundml.compat.select.steps;

import java.util.List;

/**
 * Interface that defines the execution of a DBMS query (SELECT) for a table
 * that returns a number of rows.
 *
 */
public interface CompatibleSelectFetchStep {
	
	/**
	 * Returns a list of the results from the executed query.
	 * @return - a list of the SELECT results
	 */
	public List<String> fetch();

}
