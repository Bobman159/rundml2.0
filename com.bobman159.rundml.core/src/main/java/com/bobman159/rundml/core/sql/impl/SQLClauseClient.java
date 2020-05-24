package com.bobman159.rundml.core.sql.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.expressions.impl.ExpressionSQLClauseHandler;
import com.bobman159.rundml.core.predicates.PredicateSQLClauseHandler;
import com.bobman159.rundml.core.sql.AbstractSQLClauseHandler;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Client to create SQL clause text
 *
 */
public class SQLClauseClient {

	private static SQLClauseClient self = null;
	private AbstractSQLClauseHandler chainRoot;
	
	/**
	 * 
	 * @return an instance of the Serialize client
	 */
	public static SQLClauseClient getInstance() {
		if (self == null) {
			self = new SQLClauseClient();
		}
		
		return self;
	}
	
	/** 
	 * Create SQL clause text from an SQL type
	 * @param type the SQL type
	 * @return an SQL clause as text
	 */
	public String toSQLClause(ISQLType type) {
		
		String sqlClause = "";
		sqlClause = chainRoot.start(type);
	
		return sqlClause;
		
	}
	
	/*
	 * SQL Clause client creation, creates the Chain of Responsibility
	 * 
	 */
	private SQLClauseClient() {
		chainRoot = new TypeSQLClauseHandler(
										new ExpressionSQLClauseHandler(
										new PredicateSQLClauseHandler(null)));
	}
}
