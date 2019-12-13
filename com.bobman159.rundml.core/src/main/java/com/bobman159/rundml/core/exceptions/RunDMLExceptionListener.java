package com.bobman159.rundml.core.exceptions;

import java.beans.ExceptionListener;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Default implementation of the <code>ExceptionListener</code> interface for RunDML. 
 * This implementation is added to the list of Exception listeners in RunDML and 
 * simply logs the exception and call stack information.  
 * 
 * This class may be sub-classed, but users who want or need a specific
 * exception handling behavior are recommended to implement their own implementation of 
 * <code>ExceptionListener</code> and add it to the list maintained by 
 * <code>RunDMLExceptionListeners</code>.
 * 
 * @see com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners
 *
 */
public class RunDMLExceptionListener implements ExceptionListener {
	
	private static Logger logger = LogManager.getLogger(RunDMLExceptionListener.class.getName());
	
	@Override
	public void exceptionThrown(Exception e) {
		
		logger.error(MessageFormat.format("An {0} occurred in RunDML", e.getClass().getName()));
		logger.error(e.getMessage(),e);

	}

}
