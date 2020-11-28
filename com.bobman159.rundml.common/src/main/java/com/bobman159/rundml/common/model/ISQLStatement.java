package com.bobman159.rundml.common.model;

/**
 * Main interface definition for an SQL SELECT statement in a DBMS (dialect)
 *
 */
public interface ISQLStatement {

	public enum Statement {SELECT,INSERT,UPDATE,DELETE}
	
	/**
	 * 
	 * @return the statement definition for an SQL statement
	 */
	public com.bobman159.rundml.core.model.ISQLStatement.Statement getStatement();
	
	/**
	 * 
	 * @return the dialect of the SQL statement as a String
	 */
	public String getDialect();
	
	/**
	 * 
	 * @return the statement as a text string
	 * This method should be overridden by any sub class of this interface.
	 */
	public String toSQL();
}
