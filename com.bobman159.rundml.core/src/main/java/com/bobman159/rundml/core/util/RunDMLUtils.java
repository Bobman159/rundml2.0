package com.bobman159.rundml.core.util;

import java.text.MessageFormat;

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
	 * @param schema the database schema 
	 * @param tbName the table name
	 * @return the fully qualifed table name
	 */
	public static String qualifiedTbName(String schema,String tbName) {
		return String.format("%s.%s", schema,tbName);
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

}
