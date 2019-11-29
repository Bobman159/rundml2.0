package com.bobman159.rundml.core.exprtypes;

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

public class Column implements IExpression, IMathOperations, IStringOperations {

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
