package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Defines a parameter marker "?" in an SQL statement.  Information needed for 
 * binding the parameter marker in a JDBC <code>PreparedStatement</code> is also kept
 * by this class.
 * 
 * @author Robert Anderson
 *
 */
public class ParmMarker implements ISQLType { 

	private Object value;
	private int jdbcType;
	
	/**
	 * Creates a parameter marker expression expression type.
	 * A "?" will be generated for each parameter marker expression type.
	 * @param jdbcType the JDBC Data Type to be used when binding the parameter
	 * marker.
	 * @param value the data value for the parameter marker
	 * @see java.sql.Types
	 */
	public ParmMarker(int jdbcType,Object value) {
		this.jdbcType = jdbcType;
		this.value = value;
	}
	
	/**
	 * Return the JDBC data type for this parameter marker.
	 * @return the JDBC data type
	 * @see java.sql.Types
	 */
	public int getParmType() {
		return jdbcType;
	}
	
	/**
	 * Returns the value this parameter marker.
	 * @return the value as a string
	 */
	public String getValue() {
		return value.toString();
	}
	
	public void bind() {
		//TODO Implement JDBC bind logic here
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.PARM_MARKER;
	}

}
