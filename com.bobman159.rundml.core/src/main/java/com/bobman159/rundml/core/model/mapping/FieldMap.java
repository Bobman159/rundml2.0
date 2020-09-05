package com.bobman159.rundml.core.model.mapping;

import java.lang.reflect.Field;

import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;

/**
 * A map of class fields for mapping to a table column name using a string
 * key (column name) that is not case sensitive. Key and Value types are expected
 * as Strings for this class.  <b>Only the <code>HashMap</code>
 * functions needed for storage of the fields mapping information are 
 * defined</b>
 * example
 * <pre>
* {@code
	CaseInsensitiveFieldsMap<String,String> map = new FieldsMap<String,String>();
	map.put("columnName","nameColumn");
* }
* </pre>
 */
public class FieldMap {

	private FieldMapDefinitionList 		fieldsDef;
	
	/*
	 * Private constructor for a FieldMap class 
	 * ASSUME: 1 field map uses 1 table row class which represents 1 table in a database
	 * 
	 */
	public FieldMap(Class<?> tableRowClass) throws NoTableRowClassFieldException {
		fieldsDef = CoreModelFactory.getInstance().createFieldMapDefinitionList();
		FieldMapManager.getInstance().putFieldMapping(tableRowClass, this);
		loadMap(tableRowClass);
	}
	
	/**
	 * Find the field map definition for a given java table row class
	 * @param tableRowClass the table row class to find
	 * @return the field map matching the table row class, null if no entry was found
	 */
	public static FieldMap findFieldMap(Class<?> tableRowClass) {
		return FieldMapManager.getInstance().getFieldMapping(tableRowClass);
	}
	
	/**
	 * Get the list of fields defined for this mapping
	 * @return the fields defintion information for this field map
	 */
	public FieldMapDefinitionList getFieldDefinitions() {
		return this.fieldsDef;
	}
		
	/*
	 *	Load the Field Map using information found in the supplied class
	 * 		*	By default, the class field name is used as the column name 
	 * 			for mapping a result set column to the table row class field.  
	 * 			The case is ignored when mapping.
	 * 		*	If the table row class implements the IFieldMap interface, the
	 * 			getFieldMappings method is used to obtain the list of column name(s)
	 * 			for the specified table row class field.  Again case is ignored 
	 */
	private void loadMap(Class<?> tableRowClass) throws NoTableRowClassFieldException {
		
		Object tableRow = null;
		FieldMapDefinitionList fieldOverrides = CoreModelFactory.getInstance().createFieldMapDefinitionList();
		//#1 - Get the list of IFieldMap entries (if there are any)
		if (FieldMappingUtils.isAnIFieldMap(tableRowClass)) {
			try {
				tableRow = tableRowClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				RunDMLExceptionListeners.getInstance().notifyListeners(e);
			}				
			IFieldMap fieldMapClass = (IFieldMap) tableRowClass.cast(tableRow);			
			fieldOverrides = fieldMapClass.getFieldMappings(fieldOverrides);
			validateFieldNamesDefined(fieldOverrides,tableRowClass);
		}
		
		/*	#2 - Get the list of fields in the class 
		 *	FOR each class field
		 *		IF there is an entry in the fieldsDefinition with same class Field name THEN
		 *			create a FieldMapDefinition with the column name from the alternateFieldsList - NOT NEEDED?
		 *			skip to next class Field entry
		 *		ELSE
		 *			create a new fieldsDefinition entry with the column name = class Field name
		 *		ENDIF
		 *		add FieldMapDefinition to the FieldMapDefinitionList
		 *	ENDFOR
		 *	Note:
		 *	It's possible/probable that there may be fields defined in the table row class 
		 * 	that are specific to the state of the class.  These fields would not contain 
		 * 	table column data, but based on the current logic would be included as an entry 
		 * 	in the FieldDefinitionList.  
		 * 		*	In trying to keep the mapping(s) as simple as possible, there is no logic
		 * 			to try and eliminate or identify these fields.  I don't expect there to be
		 * 			a lot of fields that will be specific to the class so I think "waste" of 
		 * 			memory will be minimal.
		 * 		*	To try and address a solution that would cover this situation would require
		 * 			RunDML to become much more of a full fledged ORM (Hibernate, jOOQ, etc) and 
		 * 			since my goal was to keep this simple, I've decided that I can live with this 
		 * 			limitation (for now)
		 * 
		 */
		Field[] classFields = FieldMappingUtils.getClassDeclaredFields(tableRowClass);
		
		for (Field classField : classFields) {
			IFieldMapDefinition wkFieldDef = null;
//				IF there is an entry in the fieldsDefinition with same class Field name THEN
//					create a FieldMapDefinition with the column name from the alternateFieldsList - NOT NEEDED?
//					skip to next class Field entry
//				 ELSE
//				 	create a new fieldsDefinition entry with the column name = class Field name
//				 ENDIF
//				 add FieldMapDefinition to the FieldMapDefinitionList

			wkFieldDef = fieldOverrides.findMapDefinitionByField(classField.getName());
			if (wkFieldDef != null) {
				fieldsDef.addDefinition(wkFieldDef);
			} else {
				/*  This is NOT foolproof, if the field name entered in a map
				*	definition doesn't match a defined field in the class 
				*/
				if (!classField.isSynthetic()) {
					fieldsDef.addDefinition(classField.getName(), classField.getName());
				}
			}
		}

	}
	
	/*
	 * Checks that the field name entered for a FieldMapDefinition entry is defined
	 * in the table row class	 
	 * 
	 * @param fieldOverrides list of overrides for a table row class
	 */
	private void validateFieldNamesDefined(FieldMapDefinitionList fieldOverrides,Class<?> tableRowClass) 
				 throws NoTableRowClassFieldException {
		
		Object[] fieldOverrideArray = fieldOverrides.getFieldDefinitions().toArray();
		for (int index = 0; index < fieldOverrideArray.length; index++) {
			IFieldMapDefinition fieldDef = (IFieldMapDefinition) fieldOverrideArray[index];
			Field classField = FieldMappingUtils.getClassField(tableRowClass, fieldDef.getClassFieldName());
			if (classField == null) {
				throw new NoTableRowClassFieldException(tableRowClass, fieldDef.getClassFieldName());
			}
		}
	}
	
}
