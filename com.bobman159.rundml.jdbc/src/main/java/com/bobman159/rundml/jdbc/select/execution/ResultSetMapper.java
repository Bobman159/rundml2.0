package com.bobman159.rundml.jdbc.select.execution;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.mapping.FieldMap;
import com.bobman159.rundml.core.mapping.IFieldMapDefinition;
import com.bobman159.rundml.core.mapping.exceptions.NoTableRowClassFieldException;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Map columns from JDBC <code>ResultSet</code> object to a java object specfied 
 * for the execution.
 *
 */
class ResultSetMapper {

	private Class<?> tableRowClass;
	private static Logger logger = LogManager.getLogger(ResultSetMapper.class.getName());
	
	public ResultSetMapper(Class<?> tableRowClass) {
		this.tableRowClass = tableRowClass;		
	}
	
	/**
	 * Map the results of the SELECT to the mapping class 
	 *
	 * @param rs a jdbc <code>ResultSet</code>
	 * @returns new instance of a mapped object
	 */
	public Object mapResultRow(ResultSet rs) {
		
		Object row = new Object();
		try {
			logger.debug(MessageFormat.format("Table row class new instance {0}: ",tableRowClass.getName()));
			row = tableRowClass.newInstance();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberColumns = rsmd.getColumnCount();

			for (int numbCol = 1;numbCol <= numberColumns;numbCol++) {
					
				String columnName = rsmd.getColumnName(numbCol);

				//Does a FieldMap already exist for this tableRowClass name?
				FieldMap fieldMap = FieldMap.findFieldMap(tableRowClass);
				if (fieldMap == null) {
					//No field map found, create the field map which will automatically map the class fields
					//to table names.					
					fieldMap = FieldMap.createFieldMap(tableRowClass);
				}
				
				//Search the FieldMap definitions for an entry matching the Result Set column name.
				IFieldMapDefinition fieldDef = fieldMap.getFieldDefinitions().findMapDefinitionByColumn(columnName);
				Field mapField = CoreUtils.getClassField(tableRowClass, fieldDef.getClassFieldName());
				logger.debug(MessageFormat.format("Mapping column {0} to field {1}",
						 columnName,mapField.getName()));
				mapColumnToField(numbCol,rs,row,mapField);
			}
		} catch (SQLException sqlex) {
			throw RunDMLException.createRunDMLException(sqlex,RunDMLException.SQL_ERROR,null);
		} catch (InstantiationException | IllegalAccessException rfex) {
			throw RunDMLException.createRunDMLException(rfex,RunDMLException.TABLE_ROW_CLASS_REFLECTION,tableRowClass.getName());
		} catch (NoTableRowClassFieldException ntrcfe) {
			throw RunDMLException.createRunDMLException(ntrcfe,RunDMLException.TABLE_ROW_CLASS_REFLECTION,tableRowClass.getName());
		}
		
		return row;
	}
	
	/*
	 * Maps or assigns a result set column to the designated field in an Object
	 * Parameters:
	 * 	index		the column number, 1 for first column, 2 for the next and so on
	 * 	rs			the ResultSet object. Assumed to be pointing at the current row.
	 * 	targetObj	Object representation of the object being mapped to (target).
	 *	targetField	Field object defining the target field in the target object.
	 *
	 *	Note:	The data assignment is done based on the JAVA type of the target field.
	 *		  	So a java.sql.Timestamp field will result in a resultSet.getTimestamp 
	 * 		  	method being called.  This allows for supporting multiple JDBC drivers.
	 *			Both primitives and wrapper classes (java.lang.Integer,java.lang.Short)
	 *			are allowed.
	 */

	private void mapColumnToField(int index,ResultSet rs,Object targetObj,
								  Field targetField) {
		
		try {

			//ASSUME: mapField is a private field matching the type of the result set
			
			/*	Using targetField.setByte(),targetField.setInt() etc  for 
			 * 	the targetObj will get an IllegalArgumentException if the field
			 * 	being set is a java wrapper class  java.lang.Byte, java.lang.Int.
			 *  targetField.set() seems to work ok for both primitive and 
			 *  wrapper classes so use that for now...
			 * 	
			 */
			targetField.setAccessible(true);
			ResultSetMetaData rsmd = rs.getMetaData();


			String targetType = targetField.getGenericType().getTypeName();
			switch(targetType) {
				case "int":
				case "java.lang.Integer":
					targetField.set(targetObj,rs.getInt(index));
					break;
				case "byte":
				case "java.lang.Byte":
					targetField.set(targetObj,rs.getByte(index));
					break;
				case "byte[]":
					targetField.set(targetObj,rs.getBytes(index));
					break;
				case "short":
				case "java.lang.Short":
					targetField.set(targetObj,rs.getShort(index));
					break;
				case "java.math.BigDecimal":
					targetField.set(targetObj,rs.getBigDecimal(index));
					break;	
				case "java.sql.Time":
					targetField.set(targetObj,rs.getTime(index));
					break;	
				case "java.sql.Date":
					targetField.set(targetObj,rs.getDate(index));
					break;	
				case "java.sql.Timestamp":
					targetField.set(targetObj,rs.getTimestamp(index));
					break;
				case "java.lang.String":
					targetField.set(targetObj,rs.getString(index));
					break;
				case "java.sql.Blob":
					targetField.set(targetObj,rs.getBlob(index));
					break;
				case "java.sql.Clob":
					targetField.set(targetObj,rs.getClob(index));
					break;	
				case "boolean":
				case "java.lang.Boolean":
					targetField.set(targetObj,rs.getBoolean(index));
					break;
				case "long":
				case "java.lang.Long":
					targetField.set(targetObj,rs.getLong(index));
					break;						
				default:
					logger.warn(CoreUtils.formatMessage(
					"Column {0} has result set type {1} that is not currently mapped by "
					+ "RunDML for field type {2} .  Object type mapping will be attempted", 
					rsmd.getColumnName(index),rsmd.getColumnTypeName(index),targetType));
			}


		} catch (SQLException sqlex) {
			RunDMLExceptionListeners.getInstance().notifyListeners(sqlex);
		} catch (IllegalAccessException iaex) {
			RunDMLExceptionListeners.getInstance().notifyListeners(iaex);
		}
	}
	

}
