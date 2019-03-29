package com.bobman159.rundml.core.tabledef;

/**
 * Exception class for when a <code>Column</code> object was not found 
 * in a <code>TableDefinition</code> 
 * 
 *
 */
public class ColumnNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a column not found exception
	 * @param column - the column name not found
	 */
	public ColumnNotFoundException(String msg) {
		super(msg);
		super.setStackTrace(this.getStackTrace());
	}

}
