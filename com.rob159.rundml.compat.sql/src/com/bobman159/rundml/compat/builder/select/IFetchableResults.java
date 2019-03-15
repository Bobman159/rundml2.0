package com.bobman159.rundml.compat.builder.select;

import java.util.List;

/**
 * Interface that defines the execution of a DBMS query (SELECT) for a table
 * that returns a number of rows.
 *
 */
public interface IFetchableResults {
	
	/**
	 * Returns a list of the results from the executed query.
	 * @return 
	 */
	public List<String> fetch();

}
