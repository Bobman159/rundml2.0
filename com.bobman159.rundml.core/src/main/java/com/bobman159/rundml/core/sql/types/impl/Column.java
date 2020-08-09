package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.sql.types.SQLType;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Represent a column in a table or SQL SQL Statement such as SELECT, INSERT, UPDATE,
 * or DELETE.  Column names are used to map query result set data to a class field in 
 * the target class for a table row.  Column names are case sensitive in RunDML.
 * 
 * 
 * <b>Type safety is not enforced by RunDML, it's done by the DBMS at runtime</b>
 * <p>You are permitted to perform operations (add,subtract,concat etc) without 
 * checking for the column type for correctness.  An error would be expected 
 * in the following scenario.
 * <p>Example
 * <p> Column strCol = new Column("strcol",Types.VARCHAR));
 * <p> strCol.add(10)
 * 
 * <p> This would create an error when run.
 */

public class Column implements ISQLType {

	private String columnName;

	/**
	 * Define a column in a table
	 * 
	 * @param column name of the column (case sensitive)
	 * 
	 */
	public Column(String column) {
		columnName = column;
	}
	
	/**
	 * 
	 * @return the column name as a string
	 */
	public String getColumnName() {
		return columnName;
	}

	@Override
	public SQLType getType() {
		return SQLType.COLUMN;
	}
	
}
