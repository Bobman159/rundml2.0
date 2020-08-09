package com.bobman159.rundml.core.model.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.bobman159.rundml.core.model.impl.CoreModelFactory;

/**
 * Provide a facility for listing field mappings from a database table column to a java class field.  The mapping
 * information is stored internally and api methods are define for retrieving a list of all field definitions,
 * finding a field definition by table column name, finding a field definition by java class field name, and
 * adding a new field definition entry to the list.
 *
 */
public class FieldMapDefinitionList {

	private List<IFieldMapDefinition> defList;
	
	/**
	 * Create a new Field mapping definition list
	 */
	public FieldMapDefinitionList() {
		defList = new ArrayList<>();
	}
	
	/**
	 * Return a non-modifiable <code>Stream</code> of the field mapping definitions
	 * for this list.
	 * 
	 * @return the <code>Stream</code> of <code>SQLClause</code> objects
	 */
	public Stream<IFieldMapDefinition> getFieldDefinitions() {
		return defList.stream();
	}
	
	/**
	 * Add a new entry to current list of field definitions
	 * @param columnName the table column name for the mapping entry
	 * @param classField the java class field name for the mapping entry
	 */
	public void addDefinition(String columnName,String classField) {
		defList.add(CoreModelFactory.getInstance().createFieldMapDefinition(columnName,classField));
	}
	
	/**
	 * Add a new entry to current list of field definitions
	 * @param fieldDef a <code>IFieldMapDefintion</code> entry
	 */
	public void addDefinition(IFieldMapDefinition fieldDef) {
		defList.add(fieldDef);
	}
	
	/**
	 * Find the first field mapping definition matching the specified column name in a database table.
	 * The matching is case insensitive, that is case does not matter.
	 * @param columnName the column name in the field definition to search for
	 * @return the first field definition found for the column name, null if no match is found for the column name
	 */
	public IFieldMapDefinition findMapDefinitionByColumn(String columnName) {
		IFieldMapDefinition fieldDefMatch = null;
		for (IFieldMapDefinition fieldDef: defList) {
			if (fieldDef.getColumnName().equalsIgnoreCase(columnName)) {
				fieldDefMatch = fieldDef;
				break;	//quit searching
			}
		}
		return fieldDefMatch;
	}
	
	/**
	 * Find the first field mapping definition matching the specified field name for a java class.
	 * The matching is case insensitive, that is case does not matter.
	 * @param classField the class field name in the field definition to search for
	 * @return the first field definition found for the class field name, null if no match is found for the field name
	 */
	public IFieldMapDefinition findMapDefinitionByField(String classField) {
		IFieldMapDefinition fieldDefMatch = null;
		for (IFieldMapDefinition fieldDef: defList) {
			if (fieldDef.getClassFieldName().equalsIgnoreCase(classField)) {
				fieldDefMatch = fieldDef;
				break;	//quit searching
			}
		}
		return fieldDefMatch;
	}
	
}
