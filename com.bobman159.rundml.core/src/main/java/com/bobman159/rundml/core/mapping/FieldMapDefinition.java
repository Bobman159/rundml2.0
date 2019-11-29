package com.bobman159.rundml.core.mapping;

/**
 * Default implementation for the IFieldMappingDefinition Interface
 * Column names and class field names are case sensitive
 *
 */
class FieldMapDefinition implements IFieldMapDefinition {

	private String columnName;
	private String classField;

	/**
	 * Create a new Field mapping definition
	 * @param columnName case sensitive table column name
	 * @param classField case sensitive class field name
	 */
	public FieldMapDefinition(String columnName,String classField) {
		this.columnName = columnName;
		this.classField = classField;
	}
	
	/**
	 * @see com.bobman159.rundml.core.mapping.IFieldMapDefinition#getColumnName()
	 */
	@Override
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @see com.bobman159.rundml.core.mapping.IFieldMapDefinition#getClassFieldName()
	 */
	@Override
	public String getClassFieldName() {
		return classField;
	}

}
