package com.bobman159.rundml.core.exceptions;

import java.beans.ExceptionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * List of <code>ExceptionListener</code> classes to be notified when an 
 * exception is thrown by RunDML.
 *
 */
public class RunDMLExceptionListeners {

	private List<ExceptionListener> listeners;
	private static RunDMLExceptionListeners self = null;
	
	/**
	 * Obtains an instance of the instance list for RunDML exception  listeners
	 * @return an instance of the RunDML listener list
	 */
	public static RunDMLExceptionListeners getInstance() {
		if (self == null) {
			self = new RunDMLExceptionListeners();
		}
		
		return self;
	}
	
	/**
	 * Constructs an instance of RunDML listeners 
	 */
	private RunDMLExceptionListeners() {
		listeners = new ArrayList<ExceptionListener>();
	}
	
	/**
	 * Add an <code>ExceptionListener</code> to the list of listeners to be notified when an 
	 * exception occurs in RunDML.
	 * @param listener the listener to be notified of exceptions
	 */
	public void addListener(ExceptionListener listener) {
		if (listeners.contains(listener) == false) {
			listeners.add(listener);
		}
	}
	
	/**
	 * Removes an <code>ExceptionListener</code> from the list of listeners to be notified when 
	 * an exception occurs in RunDML.
	 * @param listener the listener to be removed from the notification list
	 */
	public void removeListener(ExceptionListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	
	/**
	 * Notifies <code>ExceptionListener</code> currently on the listener chain.
	 * @param ex the exception thrown to notify listeners
	 */
	public void notifyListeners(SQLException ex) {
		for (ExceptionListener exListener :listeners) {
			exListener.exceptionThrown(ex);
		}
	}
	
	/**
	 * Notifies <code>ExceptionListener</code> currently on the listener chain.
	 * @param ex the exception thrown to notify listeners
	 */
	public void notifyListeners(Exception ex) {
		for (ExceptionListener exListener :listeners) {
			exListener.exceptionThrown(ex);
		}
	}
	
	/**
	 * Clears the list of current listeners
	 */
	protected void clearListeners() {
		listeners.clear();
	}
	
	/**
	 * Returns the size of the current exception listeners list
	 * @return the size of the exception listeners
	 */
	protected int getListenersSize() {
		return listeners.size();
	}
	
}
