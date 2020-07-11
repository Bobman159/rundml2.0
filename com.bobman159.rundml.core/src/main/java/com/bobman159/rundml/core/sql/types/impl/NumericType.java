package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Define a numeric value or literal that is specified an SQL statement.
 * 
 * @author Robert Anderson
 *
 */
public class NumericType implements ISQLType {
	
	private Number number;
	
	/**
	 * Create a numeric value expression from a java primitive value
	 * (int,short, double etc).
	 * 
	 * @param number java primitive type value
	 */
	public NumericType(Number number) {
		this.number = number;
	}

	@Override
	public SQLType getType() {
		return SQLType.NUMERIC;
	}

	/** 
	 * @return the number value for this instance
	 */
	public Number getNumber() {
		return this.number;
	}

}
