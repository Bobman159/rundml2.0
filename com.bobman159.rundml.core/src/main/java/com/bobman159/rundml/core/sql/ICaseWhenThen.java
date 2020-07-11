package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * 
 * @author Robert Anderson
 *
 */
public interface ICaseWhenThen {
	
	/**
	 * 
	 * @return the WHEN value as an ISQLType
	 */
	public ISQLType getWhenType();

	/**
	 * 
	 * @return the WHEN value as an ISQLCondition
	 */
	public ISQLCondition getWhenCondition();
	
	
	/**
	 * 
	 * @return the THEN expression type or expression node for the current condition
	 */
	public ISQLType getThenCondition();
	
	/**
	 * @return true if the WHEN clause value is an SQL condition, false otherwise
	 */
	public boolean isWhenValueSQLCondition();

	/**
	 * @return ture if the WHEN clause value is an SQL type, false otherwise
	 */
	public boolean isWhenValueSQLType();

}
