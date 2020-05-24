package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * WHEN expression THEN expression conditions for an SQL CASE statement
 *
 */
public interface ICaseWhenConditions {
	
	/**
	 * 
	 * @return the WHEN expression type or expression node for the current condition
	 */
	public ISQLType getWhenCondition();
	
	/**
	 * 
	 * @return the THEN expression type or expression node for the current condition
	 */
	public ISQLType getThenCondition();

}
