package com.bobman159.rundml.jdbc.execution;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exprtypes.Column;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.core.util.RunDMLUtils;

/**
 * Map columns from JDBC <code>ResultSet</code> object to a java object defined
 * in the <code>TableDefinition</code>
 *
 */
class ResultSetMapper {

	private TableDefinition tbDef;
	private Class mapClass;
	private Field[] mapFields;
	private static final String ITABLE_ROW = "com.bobman159.rundml.jdbc.select.ITableRow";
	private static Logger logger = LogManager.getLogger(ResultSetMapper.class.getName());
	
	public ResultSetMapper(TableDefinition tbDef) {
		this.tbDef = tbDef;
		mapClass = tbDef.getMapClass();
		mapFields = mapClass.getDeclaredFields();
	}
	
	/**
	 * Check if the mapping class specified in Table Definition implements 
	 * the ITable
	 * ITa
	 * @return
	 */
	public boolean isITableRow() {
		boolean valid = false;
		
		Class clazz = mapClass;
		java.lang.reflect.Type[] interfaces = clazz.getGenericInterfaces();
		for(java.lang.reflect.Type iType: interfaces) {
			if (iType.getTypeName().equals(ITABLE_ROW)) {
				valid = true;
				break;
			}
		}

		
		return valid;
	}
	
	/**
	 * Map the results of the SELECT to the mapping class defined 
	 * in the TableDefinition.
	 *
	 * @param rs a jdbc <code>ResultSet</code>
	 * @returns new instance of a mapped object
	 */
	public Object mapResultRow(ResultSet rs) {
		
		Object row = new Object();
		try {
			
			row = mapClass.newInstance();
			
			/*
			 * For now, use the "brute force" approach of checking if a 
			 * column in the ResultSet "matches or maps" to a field in 
			 * the mapping class.  NOTE: fieldName matches are case insensitive
			 * 	IF resultSet.columnName == mapClass.fieldName AND
			 * 	   resultSet.columnType == mapClass.fieldName.Type THEHN
			 * 
			 * 		set the mapClass.fieldName = resultSet.columnValue
			 * 
			 *   ELSE check the table definition, for a definition of the result 
			 *   	set column name AND that an alternate field name in the 
			 *   	map class is specified.  If a field with the alternate 
			 *   	name is found, use it. Make sure the result set type and the alternate field name
			 * 		type match.
			 *  
			 *  ELSE IF tableDefinition.columnName == resultSet.columnName AND
			 *  		tableDefinition.columnName.has(alternateColumnName) AND
			 *  		resultSet.columnType == mapClass.fieldName.Type
			 *  	
			 *  	set the mapClass.fieldName = resultSet.columnValue
			 * 
			 */
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberColumns = rsmd.getColumnCount();

			for (int numbCol = 1;numbCol <= numberColumns;numbCol++) {
					
				String columnName = rsmd.getColumnName(numbCol);
					
				/*
				 * ASSUME: only one field in the Map class is defined
				 * for each column in the result set.  
				*/
				//Check if an alternate field name is specified, if one is, use
				//it to search the fields list in the mapping class.
				Column col = tbDef.column(columnName);
				String alternateMapField = col.getMappedField();
				if (alternateMapField != null) {
					columnName = alternateMapField;
				}
						
				Field mapField = findMappedField(columnName);
				//if no field was found in the class being mapped, throw an exception
				if (mapField == null) {
					logger.error(MessageFormat.format("Field {0} was not found in class {1}", 
								columnName,tbDef.getMapClass()));
					throw new NoSuchFieldException();
				}
				mapColumnToField(numbCol,rs,row,mapField);

			}	

		} catch (InstantiationException | IllegalAccessException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (SQLException sqlex) {
			logger.error(sqlex.getMessage(),sqlex);
		} catch (NoSuchFieldException nsfex) {			
			logger.error(nsfex.getMessage(), nsfex);
		}
		
		return row;
	}
	
	
	/*
	 * Searches mapFields for the matching fieldName in the map class.
	 * The search is case insensitive and returns the first match that 
	 * it finds.
	 * 
	 * returns - the Field definition, null if no match found
	 */
	private Field findMappedField(String colName) {
		
		Field mapField = null;
		
		for (int ix = 0; ix < mapFields.length;ix++) {
			if (mapFields[ix].getName().equalsIgnoreCase(colName)) {
				mapField = mapFields[ix];
				break;
			}
		}

		return mapField;
		
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
					byte[] result = rs.getBytes(index);
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
					logger.warn(RunDMLUtils.formatMessage(
					"Column {0} has result set type {1} that is not currently mapped by "
					+ "RunDML for field type {2} .  Object type mapping will be attempted", 
					rsmd.getColumnName(index),rsmd.getColumnTypeName(index),targetType));
			}


		} catch (SQLException sqlex) {
			logger.error(sqlex.getMessage(),sqlex);
		} catch (IllegalAccessException iaex) {
			logger.error(iaex.getMessage(),iaex);			
		}
	}
	

}
