package com.bobman159.rundml.core.exprtypes;

/**
 * Base interface for SQL Expression types (number, string, column etc).
 * 
 */

public interface IExpression {

	/**
	 * Serialize the expression to an SQL statement clause.
	 * 
	 * @return - the SQL clause
	 */
	public String serialize();
	
}
