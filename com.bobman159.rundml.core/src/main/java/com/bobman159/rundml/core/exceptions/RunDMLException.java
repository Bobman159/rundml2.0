package com.bobman159.rundml.core.exceptions;

/**
 * A wrapper class for RunDML exceptions that are encountered during SQL Building or execution.
 *
 */
public class RunDMLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Indicates Table Row Reflection error
	 */
	public static final int	TABLE_ROW_CLASS_REFLECTION = 1000;
	/**
	 * A generic SQL Exception error
	 */
	public static final int	SQL_ERROR = 1001;
	/**
	 * Indicates SQL Building Error
	 */
	public static final int	SQL_MODEL_BUILD = 1002;
	
	private final int execPhase;			//Code indicating when error occurred
	private final Object[] args;			//Argument(s) if any for the error
	
	/**
	 * Create an instance of a RunDMLException with a specified reason
	 * 
	 * @param reason the reason the exception occurred
	 * @param execPhase the type of error
	 * @param args arguments for error, null may be used if no arguments
	 * @return a RunDMLException
	 */
	public static RunDMLException createRunDMLException(Throwable reason, int execPhase,Object...args) {
		return new RunDMLException(execPhase,args,reason);
	}
	
	private RunDMLException(int execPhase,Object[] args,Throwable reason) {
		super(reason);
		this.execPhase = execPhase;
		this.args = args;
	}
	
	/**
	 * Returns text indicating the type of error that occurred
	 * @return - a text string of the error type, " " is returned for unknown error codes
	 */
	public String getRunDMLMessage() {
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("RunDML encountered a ");
		
		if (execPhase == TABLE_ROW_CLASS_REFLECTION) {
			errorMessage.append("Table Row Class Reflection error");
			errorMessage.append(" in class " + args[0]);
		} else if (execPhase == SQL_ERROR) {
			errorMessage.append("SQL Exception error during execution");
		} else if (execPhase == SQL_MODEL_BUILD) {
			errorMessage.append("Error during build of SQL statement model");
		}
		
		return errorMessage.toString();
	}
	
	/**
	 * Return the execution phase code when the exception occurred
	 * @return the execution phase code
	 */
	public int getExecutionPhase() {
		return execPhase;
	}
	

	
}
