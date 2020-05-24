package com.bobman159.rundml.core.sql.types;

/**
 * An SQL type, either abstract (a table name,parameter marker,SQL clause) or concrete (Number, String, etc).
 * This interface should be implemented by any class representing an SQL type  
 *  
 *
 */
public interface ISQLType {
	
	/**
	 * The SQL type represented by classes implementing this interface.
	 * @return
	 */
	public SQLType getType();
	
}
