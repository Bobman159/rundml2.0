package com.bobman159.rundml.core.sql.types;

import com.bobman159.rundml.core.sql.ICaseWhenValue;

/**
 * An SQL type, either abstract (a table name,parameter marker,SQL clause) or concrete (Number, String, etc).
 * This interface should be implemented by any class representing an SQL type  
 */
public interface ISQLType extends ICaseWhenValue {
	
	/**
	 * The SQL type represented by classes implementing this interface.
	 * @return the type for SQL type
	 */
	public SQLType getType();
	
}
