package com.bobman159.rundml.core.sql.sql.conditions;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Defines an SQL condition such as a predicate clause
 *
 */
public interface ISQLCondition {


	/**
	 * 
	 * @return the left side of the SQL condition
	 */
	public ISQLType	getLeftCondition();
	
	/**
	 * 
	 * @return the right side of the SQL condition
	 */
	public ISQLType getRightCondition();
	
	/**
	 * 
	 * @return the operator for the SQL condition
	 */
	public Op		getOperator();
}
