package com.bobman159.rundml.core.tabledef;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.util.RunDMLUtils;

/**
 * Defines the java class and optionally it's fields that will be 
 * used by RunDML.  RunDML uses this class to map results from an 
 * SQL <code>ResultSet</code> to an implementation of an <code>ITableRow</code>
 * interface.  By default the field name(s) in the object being mapped, should 
 * match the name of the column in the table for the value being contained.
 * If this is not the case, the addMapColumn may be used to specify the field 
 * name for the class.
 * 
 * TableMapper is required by RunDML for any SQL execution.  
 * @see com.bobman159.rundml.jdbc.select.ITableRow  
 */

class TableMapper {

	private String schema;
	private String tbName;
	private Class  mappingClass;
	private LinkedHashMap<String,Column> columnMap;
	private static Logger logger = LogManager.getLogger(TableMapper.class);
	
	/**
	 * Defines the implementation of <code>ITableRow</code> object that 
	 * will be mapped.
	 * @param schmea the table schema
	 * @param tbName the name of the table
	 * @param mapClass the class for mapping results for this table
	 */
	@SuppressWarnings("rawtypes")
	public TableMapper(String schema, String tbName, Class mapClass) {
		this.schema = schema;
		this.tbName = tbName;
		this.mappingClass = mapClass;
	}
	
	/**
	 * Specify an alternate/non-default field name in the class being mapped
	 * for use by RunDML.
	 * 
	 * @param columnName name of column 
	 * @param jdbcType the JDBC type matching column's data type
	 * @param mapField the field name in the mapping class 
	 * @see com.bobman159.rundml.jdbc.select.ITableRow
	 */
	public void addMapColumn(String columnName,int jdbcType,String mapField) {
		columnMap.put(columnName, new Column(columnName,jdbcType,mapField));
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
	
	/**
	 * Obtain the name of the mapping class.
	 * @return the class name as a string
	 */
	public String getMapClassName() {
		return mappingClass.getName();
	}

}
