package com.bobman159.rundml.sql.mysql;

import com.bobman159.rundml.core.sql.ISQLClause;

/**
 * Defines enumerations for SQL clauses specific to MySQL databases for 
 * SELECT, INSERT, UPDATE,and DELETE statements.  
 * 
 * This class is not intended to be sub classed.
 *
 */
public class MySQLClauses {

	/**
	 * Define MySQL DBMS specific SQL clauses.  Clauses are defined in alphabetical order.	 *
	 */
	public enum MySQLClause implements ISQLClause {
		BIG_RESULT("sql_big_result"),
		BUFFER_RESULT("sql_buffer_result"),
		LIMIT("limit"),
		SMALL_RESULT("sql_small_result"),
		OFFSET("offset");
		
		private String clause = "";
		
		private MySQLClause(String clause) {
			this.clause = clause;
		}
		
		/**
		 * Return the SQL clause enumeration as a text string
		 */
		public String serializeClause() {
			return this.clause;
		}
		
		
	}
}
