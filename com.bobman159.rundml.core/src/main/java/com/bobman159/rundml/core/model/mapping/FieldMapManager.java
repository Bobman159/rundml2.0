package com.bobman159.rundml.core.model.mapping;

import java.util.HashMap;

/**
 * Manages field mapping definitions for table row classes being used by RunDML.
 * One entry is needed for each each table row class.
 *
 */
class FieldMapManager {

	private HashMap<String,FieldMap> fieldMap;
	private static FieldMapManager self;
	
	private FieldMapManager() {
		fieldMap = new HashMap<>();
	}
	
	/**
	 * Obtain a reference for the field mapping manager
	 * @return a field mappings manager instance
	 */
	protected static FieldMapManager getInstance() {
		if (self == null) {
			self = new FieldMapManager();
		}
		return self;
	}
	
	/**
	 * Stores field mappings between a database table columns and 
	 * java class fields.
	 * 
	 * @param tableRowClass the table row class 
	 * @param fieldMap the field mappings for the table row class
	 */
	public void putFieldMapping(Class<?> tableRowClass,FieldMap fieldMap) {
		this.fieldMap.put(tableRowClass.getName(),fieldMap);
	}

	/**
	 * Returns the <code>FieldMap</code> which is mapped to the specified class name
	 * 
	 * @param tableRowClass the table row class name for the desired field map.
	 * @return the <code>FieldMap</code> for the specified class, null if no entry was found
	 */
	public FieldMap getFieldMapping(Class<?> tableRowClass) {		

		return fieldMap.get(tableRowClass.getName());
	
	}
	
	
	

}
