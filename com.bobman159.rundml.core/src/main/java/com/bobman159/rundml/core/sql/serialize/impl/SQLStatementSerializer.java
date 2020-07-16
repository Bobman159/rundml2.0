package com.bobman159.rundml.core.sql.serialize.impl;

/**
 * Serializes or creates an SQL Statement (SELECT, INSERT, UPDATE or DELETE) 
 * from an instance of an SQL statement model.
 *
 */
public class SQLStatementSerializer extends BaseSQLSerializer {
	
	private static SQLStatementSerializer self;
	
	/**
	 * 
	 * @return reference to the SQL statement
	 */
	public static SQLStatementSerializer getInstance() {
		if (self == null) {
			self = new SQLStatementSerializer();
		}
		return self;
	}
	
	
	/**
	 * Serialize an SQL SELECT statement from a model.
	 * @param model the model to build the SELECT statement from
	 * @return text of the SELECT statement
	 */
	public static String serializeSelect() {
		
		StringBuilder builder = new StringBuilder();		
		return builder.toString().trim();
	}
	

	
}
