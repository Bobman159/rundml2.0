package net.bobs.own.db.rundml.exception;

import java.sql.SQLException;

public class RunDMLException extends Throwable {

	private Exception cause; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RunDMLException(SQLException cause) {
		this.cause = cause;
	}
	
	public RunDMLException(Exception ex) {
		this.cause = ex;
	}
	
	public RunDMLException(String message) {
		super(message);
	}

	public Exception getCause() {
		return this.cause;
	}
}
