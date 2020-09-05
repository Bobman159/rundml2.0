package com.bobman159.rundml.core.sql;

/**
 * Template information to serialize or create SQL statement text
 *
 */
public interface IStatementSerializer {
	
	/**
	 * 
	 * @return the full SQL statement text
	 */
	public String generateSQLStatementText();
	
}
