package com.bobman159.rundml.core.mapping.exceptions;

import java.text.MessageFormat;

/**
 * Indicates that a table row class does not have a field matching the specified name.
 *
 */
public class NoTableRowClassFieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTableRowClassFieldException(Class<?> tableRowClass,String fieldName) {
		super(MessageFormat.format("No Field named {0} found in class {1}", fieldName,tableRowClass.getName()));
	}

}
