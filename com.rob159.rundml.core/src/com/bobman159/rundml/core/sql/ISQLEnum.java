package com.bobman159.rundml.core.sql;

/**
 * Define a standard interface for Java Enum types that will be used to convert 
 * the Enum to text to build an SQL statement in text.
 * 
 *
 */
public interface ISQLEnum {

	/**
	 * Serialize the ISQLEnum of an SQL clause to a String of text.
	 * 
	 * @return - the text for the SQL clause syntax
	 */
	public String serializeClause();
	
}
