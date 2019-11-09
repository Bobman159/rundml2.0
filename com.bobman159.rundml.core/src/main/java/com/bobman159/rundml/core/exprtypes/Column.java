package com.bobman159.rundml.core.exprtypes;

/**
 * Represent a column in a table and it's <b>JDBC</b> data type. The jdbc type
 * is used to simplify mapping and binding of parameters across different
 * Database Management Systems (DBMS)
 * 
 * <b>Type safety is enforced only at runtime</b>
 * <p>You are permitted to perform operations (add,subtract,concat etc) without 
 * checking for the column type for correctness.  An error would be expected 
 * in the following scenario.
 * <p>Example
 * <p> Column strCol = new Column("strcol",Types.VARCHAR));
 * <p> strCol.add(10)
 * 
 * <p> This would create an error when run.
 */

public class Column implements IExpression, IMathOperations, IStringOperations {

	private String columnName;

	/**
	 * Define a column in a table
	 * 
	 * @param column name of the column (case insensitive)
	 * 
	 */
	public Column(String column) {
		columnName = column.toUpperCase();
	}
	
	/**
	 * Return the name of the table column for this column.
	 * 
	 * @return the column name
	 */
	public String getName() {
		return columnName;
	}

	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		return columnName;
	}
	
}
