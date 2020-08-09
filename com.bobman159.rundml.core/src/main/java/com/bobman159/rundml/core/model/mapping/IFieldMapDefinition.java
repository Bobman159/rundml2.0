package com.bobman159.rundml.core.model.mapping;

/**
 * An Interface to define a mapping between a database table column 
 * and the table row class field that will be used to contain the columns 
 * value.  Column names and class field names are case sensitive.
 * 
 */
//TODO: Consider deleting this interface?
public interface IFieldMapDefinition {

	/**
	 * Return the table column name to use for mapping from a SQL Query Result
	 * @return the column name
	 */
	public String getColumnName();
	
	/**
	 * Return the class field name in a table row class
	 * @return the class field name
	 */
	public String getClassFieldName();
	
}
