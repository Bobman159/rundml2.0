package com.bobman159.rundml.common.model;

/**
 * Template information to serialize or create SQL statement text
 *
 */
public interface IStatementSerializer {
	
	/**
	 * Serialize an SQL SELECT statement as text from the Select statement definition.
	 * @param selectStatement the SELECT definition
	 * @return the SELECT as SQL text
	 */
	public String serialize();
	
	//TODO: Add serializeInsert, serializeUpdate, serializeDelete declarations
	
}
