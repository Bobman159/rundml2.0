package com.bobman159.rundml.core.exprtypes;

/**
 * Base interface for SQL Expression types (number, string, column etc).
 * This class should be implemented for any SQL expression types 
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
