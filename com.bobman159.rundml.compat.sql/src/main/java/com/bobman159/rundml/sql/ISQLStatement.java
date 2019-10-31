package com.bobman159.rundml.sql;

/**
 * Define a base interface that <b>all</b>  SQL (INSERT, UPDATE, DELETE, etc) statements 
 * must implement
 *
 */
public interface ISQLStatement {

	/**
	 * Returns then SQL statement in text form
	 * @return the SQL statement text
	 */
	public String getStatementText();

}
