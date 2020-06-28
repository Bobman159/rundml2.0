package com.bobman159.rundml.core.sql.types;

import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.core.sql.types.impl.StringType;
import com.bobman159.rundml.core.sql.types.impl.Table;

public interface ISQLTypesFactory {

	/**
	 * Create a column expression defining a column name in a database table
	 * @param columnName string column name in table
	 * @return a <code>Column</code> expression
	 */
	public default ISQLType column(String columnName) {
		return new Column(columnName);
	}
	
	/**
	 * Create a numeric value expression from a java primitive type.
	 * Integer, double, etc.
	 * 
	 * @param numb the number value
	 * @return a <code>NumericType</code> expression value
	 */
	public default NumericType constant(Number numb) {
		return new NumericType(numb);
	}

	/**
	 * Create a string value expression type from a java primitive type 
	 * @param value the string value
	 * @return a <code>StringType</code> expression value
	 */
	public default ISQLType constant(String value) {
		return new StringType(value);
	}
	
	/**
	 * Crate unqualified database table value type 
	 * @param tableName the database table name 
	 * @return a <code>Table</code> type
	 */
	public default Table unQualifiedTable(String tableName) {
		return new Table(tableName);
	}
	
	/**
	 * Create a qualified (schema.tableName) database table value type
	 * @param schema the database schema for the table
	 * @param tableName the database table name
	 */
	public default Table qualifiedTable(String schema, String tableName) {
		return new Table(schema, tableName);
	}
	
	/**
	 * Create a SQL parameter marker expression for the SQL statement.
	 * A "?" will be generated in the SQL statement for each parameter marker.
	 * 
	 * @param jdbcType the JDBC type for the parameter marker
	 * @param value the data value for the parameter marker.
	 * 
	 * @see java.sql.Types
	 * @return a <code>ParmMarker</code> expression 
	 */
	public default ParmMarker parm(int jdbcType,Object value) {
		return new ParmMarker(jdbcType,value);
	}

}
