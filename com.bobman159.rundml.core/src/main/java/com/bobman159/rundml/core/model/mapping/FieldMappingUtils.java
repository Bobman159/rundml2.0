package com.bobman159.rundml.core.model.mapping;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FieldMappingUtils {

	private FieldMappingUtils() {
		//Make SonarLint Happy
	}
	
	/**
	 * @see java.lang.Class#getDeclaredFields()
	 * @param clazz a table row class name
	 * @return an array of <code>Field</code> entries for the class
	 */
	public static Field[] getClassDeclaredFields(Class<?> clazz) {

		Field[] classFields = clazz.getDeclaredFields();
		ArrayList<Field> nonSynthClassFields = new ArrayList<>();

		for (int ix = 0; ix < classFields.length;ix++) {
			if (!(classFields[ix].isSynthetic())) {
				nonSynthClassFields.add(classFields[ix]);
			}			
		}
		
		Field[] arrNonSynthClassFields = new Field[nonSynthClassFields.size()];
		for (int ix = 0; ix < nonSynthClassFields.size(); ix++) {
			arrNonSynthClassFields[ix] = nonSynthClassFields.get(ix);
		}
		
		return arrNonSynthClassFields;
	}
	
	/**
	 * Returns the java.lang.reflect.Field object for the field name being searched for
	 * in a table row class.
	 * 
	 * @see java.lang.Class#getField(String)
	 * @param tableRowClass table row class name
	 * @param fieldName field name to find in the table row class
	 * @return the <code>Field</code> entry for the field name, null if no matching field found
	 */
	public static Field getClassField(Class<?> tableRowClass,String fieldName) {
		Field classField = null;
		
		try {
			classField = tableRowClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException ex) {
			//Do nothing, callers will handle this method returning null
		}
		
		return classField;
	}
	
	/**
	 * Test if a specified class implements the <code>IFieldMap</code> interface
	 * @param tableRowClass the class to test if it implements the interface
	 * @return true if the class implements <code>IFieldMap</code>, false if it does not
	 */
	public static boolean isAnIFieldMap(Class<?> tableRowClass) {
		
		boolean isIFieldMap = false;
		
		if (IFieldMap.class.isAssignableFrom(tableRowClass)) {
			isIFieldMap = true;
		}
		
		return isIFieldMap;
	}
}
