package net.bobs.own.db.rundml.sql.expression.types;

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

	/**
	 * Define a column in a table
	 * 
	 * @param column  - name of the column (case insensitive)
	 * @param colType - <b>jdbc type</b> of column used for jdbc data binding
	 * 
	 */
	public Column(String column, int colType) {
		columnName = column.toUpperCase();
		jdbcType = colType;
	}

	/**
	 * Return the name of the table column for this column.
	 * 
	 * @return - the column name
	 */
	public String getColumn() {
		return columnName;
	}

	/**
	 * Return the JDBC data binding type for this column.
	 * 
	 * @return - the jdbc type.
	 */
	public int getType() {
		return jdbcType;
	}

	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		return columnName;
	}
	
}
