package com.bobman159.rundml.core.sql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Abstract handler to create a text SQL statement clause.
 * Uses Chain of Responsibility pattern, the request is handled
 * by only one of the handlers in the chain.
 *
 */
public abstract class AbstractSQLClauseHandler {

	private AbstractSQLClauseHandler next = null;
	private boolean isHandled = false;
	private static Logger logger = LogManager.getLogger(AbstractSQLClauseHandler.class.getName());
	
	/**
	 * Create a new SQL clause handler that adds the next handler in the chain as it is added
	 * @param handler the handler to be added to the chain, null for the last Handler in the chain
	 */
	public AbstractSQLClauseHandler(AbstractSQLClauseHandler handler) {
		this.next = handler;
		isHandled = false;
	}
	
	/**
	 * Indicate if the current instance handler has handled this request
	 * @param handled true if it will be handled, false otherwise
	 */
	public void setHandled(boolean handled) {
		isHandled = handled;
	}
	
	/**
	 * Start calling handlers in Chain of Responsibility to handle a sql clause generation
	 * request.
	 * @param typeNode the node to generate SQL clause text from
	 * @return the generated SQL clause text
	 */
	public String start(ISQLType typeNode) {
		
		String sqlClause = "";
		AbstractSQLClauseHandler nextHandler = next();
		
		try {
			sqlClause = toSQLClause(typeNode);
		} catch (RunDMLException rdex) {
			logger.error(rdex.getMessage(), rdex);
		}
		while (nextHandler != null && !wasHandled()) {
			sqlClause = nextHandler.start(typeNode);
			nextHandler = nextHandler.next();
		}
		
		return sqlClause;
	}

	public AbstractSQLClauseHandler next() {
		
		return this.next;
	}		

	
	private boolean wasHandled() {
		return this.isHandled;
	}
	
	/**
	 * Create a SQL clause text from a SQL type 
	 * @param a <SQLModeNode> to be translated to a string 
	 * @return an SQL clause string
	 */
	public abstract String toSQLClause(ISQLType typeNode) throws RunDMLException;
	
}
