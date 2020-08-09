package com.bobman159.rundml.core.util;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListener;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;
import com.bobman159.rundml.core.model.mapping.FieldMap;
import com.bobman159.rundml.core.model.mapping.FieldMapDefinitionList;
import com.bobman159.rundml.core.model.mapping.IFieldMap;
import com.bobman159.rundml.core.model.mapping.IFieldMapDefinition;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

//TODO: Refactor this & move methods to Utils* classes where
// -	IE  Method in Coreutils used for field mapping should belong in a FieldMappingUtils
// -	Eliminate this class if possible
/**
 * A utility class for use in building the SQL statements for RunDML.
 * 
 *
 */
public class CoreUtils {
	
	private CoreUtils() {
		//To make Sonar Lint happy
	}
	
	/**
	 * Perform initialization and setup for RunDML
	 */
	public static void initRunDML() {

		RunDMLExceptionListeners.getInstance().addListener(new RunDMLExceptionListener());
		
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

	/**
	 * Creates a list of ISQLType column entries for a specified table row class.
	 * A table row class field name is used as the column name by default, unless an 
	 * entry is added to the Field Map in which case the column name is from that entry is used.
	 * 
	 * @param tableRowClass the table row class
	 * @return an array of column names as an ISQLType[] array
	 * @throws RunDMLException when errors occur building the field map
	 */
	
	public static ISQLType[] createColumnsFromClass(Class<?> tableRowClass) throws RunDMLException {
		ISQLType[] exprs;
		int index = 0;
		
		FieldMap fieldMap = FieldMap.findFieldMap(tableRowClass);
		if (fieldMap == null) {
			try {
				fieldMap = CoreModelFactory.getInstance().createFieldMap(tableRowClass);
			} catch (NoTableRowClassFieldException e) {
				throw RunDMLException.createRunDMLException(e, RunDMLException.SQL_MODEL_BUILD);
			}
		}
		FieldMapDefinitionList defsList = fieldMap.getFieldDefinitions();
		Object[] fieldDefs = defsList.getFieldDefinitions().toArray();
		exprs = new ISQLType[fieldDefs.length];
		Column col = null;
		for (Object fieldDef : fieldDefs) {
			if (fieldDef instanceof IFieldMapDefinition) {
				IFieldMapDefinition wkFieldDef = (IFieldMapDefinition) fieldDef;
				col = (Column) SQLTypeFactory.getInstance().column(wkFieldDef.getColumnName());
			}
				
			exprs[index] = col;
			index++;
		}
		
		return exprs;

	}

	/**
	 * Formats message text for display using a variable number of arguments
	 * @param formatMsg the message text to be displayed
	 * @param args variable length argument list for the message
	 * @return a formatted string for display
	 */
	public static String formatMessage(String formatMsg,Object... args) {
		
		return MessageFormat.format(formatMsg, args);
	}

	/**
	 * Create an array of ISQLType[] table column names from a String array.
	 * No case sensitivity is expected for the list of column names
	 * @param columns a string of array table column names
	 * @return an array of <code>ISQLType</code> column names
	 */
	public static ISQLType[] createColumnsFromStrings(String[] columns) {
		
		ISQLType[] exprs = new ISQLType[columns.length];
		int index = 0;
		
		for (String columnName : columns) {
			exprs[index] = SQLTypeFactory.getInstance().column(columnName);
			index++;
		}
		
		return exprs;
	}
	
	/**
	 * Normalizes or removes leading, trailing and extra spaces "  " from an SQL clause
	 * @param sqlClause a non normalized sql clause
	 * @return a normalized SQL clause string
	 */
	public static String normalizeString(String sqlClause) {

		String normalizedSQLClause = sqlClause;

		normalizedSQLClause = normalizedSQLClause.replaceAll("( )+", " ");
		normalizedSQLClause = normalizedSQLClause.trim();

		return normalizedSQLClause;
		}

}
