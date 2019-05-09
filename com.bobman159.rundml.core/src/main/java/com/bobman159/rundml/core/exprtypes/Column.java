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
	int jdbcType;
	String mappedField;

	/**
	 * Define a column in a table
	 * 
	 * @param column name of the column (case insensitive)
	 * @param colType <b>jdbc type</b> of column used for jdbc data binding
	 * 
	 */
	public Column(String column, int colType) {
		columnName = column.toUpperCase();
		jdbcType = colType;
		mappedField = null;
	}
	
	/**
	 * Define a column in a table and specify a field name to be mapped.
	 * @param column name of the column (case insensitive)
	 * @param colType <b>jdbc type</b> of the column used for jdbc data binding
	 * @param mapField class name field to contain values for column (case insensitive)
	 */
	public Column(String column, int colType, String mapField) {
		columnName = column.toUpperCase();
		jdbcType = colType;
		mappedField = mapField;
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
	 * Return the JDBC data binding type for this column.
	 * 
	 * @return the jdbc type.
	 */
	public int getType() {
		return jdbcType;
	}
	
	/**
	 * Return the alternate mapped field name for this column.  By 
	 * default columns names are mapped to the field name matching the column name.
	 * @return the alternate mapped name, null if no alternate mapping defined
	 */
	public String getMappedField() {
		return mappedField;
	}

	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		return columnName;
	}
	
}
