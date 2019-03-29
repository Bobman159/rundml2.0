package com.bobman159.rundml.core.util;

/**
 * A utility class for use in building the SQL statements for RunDML.
 * 
 *
 */
public class RunDMLUtils {

	private RunDMLUtils() {
		//To make Sonar Lint happy
	}
	
	/**
	 * Returns a qualified table name (schema.tableName)
	 * @param schema
	 * @param tbName
	 */
	public static String qualifiedTbName(String schema,String tbName) {
		return String.format("%s.%s", schema,tbName);
	}

}
