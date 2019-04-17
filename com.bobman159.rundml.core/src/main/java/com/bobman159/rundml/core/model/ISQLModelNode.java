package com.bobman159.rundml.core.model;

import com.bobman159.rundml.core.sql.ISQLEnum;

/**
 * Information for an SQL clause in an SQL statement. Each instance  of
 * <code>ISQLModelNode</code> represents the text of the SQL clause for the SQL 
 * statement and the "argument(s)" or value(s) for that SQL clause. The arguments 
 * will be serialized from the Java object into the text for the SQL clause  
 * 
 * Combined together into a <code>SQLStatementModel</code> the ISQLModelNode is used
 * to build the text of an SQL Statement will be executed.
 *
 */
public interface ISQLModelNode {
	 
	/**
	 * Return the base type <code>ISQLEnum</code> of the java Enum for an SQL 
	 * clause
	 * @return a ISQLEnum of a Java enum for an SQL clause.
	 */
	public ISQLEnum 	getEnum();

	/**
	 * Return the argument if any for the SQL clause
	 * @return the argument for this instance returned as a String, null otherwise
	 */
	public String argToString();
	
	/**
	 * Return the argument if any for the SQL clause
	 * @return the argument for this instance returned as a String, null otherwise
	 */
	public Object getArg();
}
