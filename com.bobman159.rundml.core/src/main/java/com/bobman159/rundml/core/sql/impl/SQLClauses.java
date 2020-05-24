package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.sql.ISQLClause;

/**
 * Defines enumerations for SQL clauses in SELECT, INSERT, UPDATE and DELETE 
 * statements.  Specific DBMS syntax SQL clause enumerations are defined 
 * for the specific DBMS.
 * 
 * This class is not intended to be sub classed.
 *
 */
public class SQLClauses {
	
	/**
	 * Define non DBMS specific SQL clauses.  Clauses are defined in alphabetical order.	 *
	 */
	public enum SQLClause implements ISQLClause  {
		//TODO: Add AS clause support
		ALL("all"),
		DISTINCT("distinct"),
		FROM("from"),
		GROUPBY("group by"),
		HAVING("having"),
		ORDERBY("order by"),		
		SELECT("select"),
		SELECTSTAR("*"),
		SELECTEXPR(" "),
		WHERE("where"),
		/** An SQL CASE expression */
		CASE_EXPR("CASE"),
		/** An SQL CASE WHEN THEN expression */
		CASE_WHEN("CASE WHEN"),
		/** An SQL Conditional expression */
		CONDITION_EXPR("");
		
		String clause = "";
		
		private SQLClause(String clause) {
			this.clause = clause;
		}
		
		/**
		 * Return the SQL clause enumeration as a text string.
		 */
		public String serializeClause() {
			return clause;
		}
		
	}

}
