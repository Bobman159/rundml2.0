package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Defines a string constant in an SQL statement 
 * @author Robert Anderson
 *
 */
public class StringType implements ISQLType {
	
	private String strConst;
	
	/**
	 * Defines the String constant 
	 * @param strConstant the value of the string constant
	 */
	public StringType(String strConstant) {
		this.strConst = String.format("\'%s\'", strConstant);
	}

	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.STRING;
	}
	
	public String getConstant() {
		return this.strConst;
	}

}
