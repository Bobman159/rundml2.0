package com.bobman159.rundml.core.tabledef;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.util.RunDMLUtils;

/**
 * Defines a database table with the columns that may be used in SQL 
 * statements that are executed by RunDML.  The purpose of TableDefintion is 
 * used for easier generation of multiple columns in a table in SQL statements.
 * It allows for defining a list of columns and then passing the instance to
 * a SQL statement builder which will use all the columns defined in the 
 * table definition.
 * 
 * TableDefinition is <b>not</b> required for SQL execution or generation.
 *
 */
public class TableDefinition {
	
	private String schema;
	private String tbName;

	private static Logger logger = LogManager.getLogger(TableDefinition.class);
	LinkedHashMap<String,Column> columnMap;

	/**
	 * Define the Table 
	 * @param schema the table schema 
	 * @param tbName name of the table
	 * 
	 */
	public TableDefinition(String schema,String tbName) {
		this.schema = schema;
		this.tbName = tbName;
		columnMap = new LinkedHashMap<>();
	}
	
	/**
	 * Add a column to the table definition.  
	 * @param columnName name of column as defined in table
	 * @param jdbcType the JDBC type matching column's data type
	 * 
	 * @see com.bobman159.rundml.core.exprtypes.Column
	 */
	public void addColumn(String columnName,int jdbcType) {
		columnMap.put(columnName, new Column(columnName,jdbcType));
	}
		
	/**
	 * The fully qualified table name (schema.table)
	 * 
	 * @return the fully qualified table name.
	 */
	public String qualifedTableName() {
		return RunDMLUtils.qualifiedTbName(schema, tbName);
	}
	
	/**
	 * Returns a non modifiable list of the <code>Column</code>s defined 
	 * for this table definition.
	 * 
	 * @return the list of defined columns
	 */
	public Stream<Column> columns() {
		return columnMap.values().stream();
	}
	
	/**
	 * Obtain column information for a column in the table definition.
	 * Column names are case sensitive.
	 * 
	 * @param columnName the name of the column
	 * @return <code>Column</code> information for the column, null otherwise
	 */
	/*
	 * At this time I don't feel this violates encapsulation since there are
	 * no set methods in the Column class.
	 */
	public Column column(String columnName) {
		
		Column col = null;

		col = columnMap.get(columnName);
		if (col == null) {
			String msg = String.format("Column name %s was not found for table %s", 
						columnName,RunDMLUtils.qualifiedTbName(schema,tbName));
			try {
					throw new ColumnNotFoundException(msg);
			} catch (Exception ex) {
				logger.error(ex);
			}
 
		} 

		return col;
	}

}
