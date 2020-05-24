package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * A qualified (schema.tableName) or unqualified (tableName) table name in 
 * an SQL statement 
 *
 */
public class Table implements ISQLType {
	private String schema;
	private String tbName;
	
	/**
	 * A qualified table name in an SQL statement
	 * @param schema the schema of the table
	 * @param tbName the table name
	 */
	public Table(String schema,String tbName) {
		this.schema = schema;
		this.tbName = tbName;
	}
	
	/**
	 * An unqualified table name in an SQL statement
	 * @param tbName the table name
	 */
	public Table(String tbName) {
		this.schema = null;
		this.tbName = tbName;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.TABLE;
	}

	
	/**
	 * Returns a qualified table name (schema.tableName)
	 * @param schema the database schema 
	 * @param tbName the table name
	 * @return the fully qualified table name
	 */
	public String tableName() {
		String tableName = "";
		
		if  (schema != null) {
			tableName = schema + ".";
		}
		
		if (tableName != null) {
			tableName = tableName + tbName;
		}
		
		return tableName;
	}
	
}
