package com.bobman159.rundml.core.tabledef;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.util.RunDMLUtils;

/**
 * Defines a database table with the columns that may be used in SQL statements
 * that are executed by rundml.   
 *
 */
public class TableDefinition {
	
	private String schema;
	private String tbName;
	LinkedHashMap<String,Column> columnMap;

	/**
	 * Define the Table 
	 * @param schema - the table schema 
	 * @param tbName - name of the table
	 * 
	 */
	public TableDefinition(String schema,String tbName) {
		this.schema = schema;
		this.tbName = tbName;
		columnMap = new LinkedHashMap<String,Column>();
	}
	
	/**
	 * Add a column to the table definition.  
	 * @param columnName - name of column as defined in table
	 * @param jdbcType - the JDBC type matching column's data type
	 * 
	 * @see com.bobman159.rundml.core.exprtypes.Column
	 */
	public void addColumn(String columnName,int jdbcType) {
		columnMap.put(columnName, new Column(columnName,jdbcType));
	}
	
	/**
	 * The fully qualified table name (schema.table)
	 * 
	 * @return - fully qualified table name.
	 */
	public String qualifedTableName() {
		return RunDMLUtils.qualifiedTbName(schema, tbName);
	}
	
	/**
	 * Returns a non modifiable list of the <code>Column</code>s defined 
	 * for this table definition.
	 * 
	 * @return - the list of defined columns
	 */
	public Stream<Column> columns() {
		return columnMap.values().stream();
	}
	
	/**
	 * Obtain column information for a column in the table definition.
	 * Column names are case sensitive.
	 * 
	 * @param columnName - the name of the column
	 * @return - <code>Column</code> information for the column, null otherwise
	 */
	/*
	 * At this time I don't feel this violates encapsulation since there are
	 * no set methods in the Column class.
	 */
	public Column getColumnName(String columnName) {
		return columnMap.get(columnName);
	}
	
	
	

}
