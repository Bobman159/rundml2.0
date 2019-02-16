package net.bobs.own.db.rundml.sql.expression.types;

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
