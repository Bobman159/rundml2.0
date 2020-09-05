package com.bobman159.rundml.core.util;

import java.text.MessageFormat;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListener;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;

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
	 * Formats message text for display using a variable number of arguments
	 * @param formatMsg the message text to be displayed
	 * @param args variable length argument list for the message
	 * @return a formatted string for display
	 */
	public static String formatMessage(String formatMsg,Object... args) {
		
		return MessageFormat.format(formatMsg, args);
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
