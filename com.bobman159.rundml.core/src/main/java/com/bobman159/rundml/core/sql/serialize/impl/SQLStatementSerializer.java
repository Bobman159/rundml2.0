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
	
}
