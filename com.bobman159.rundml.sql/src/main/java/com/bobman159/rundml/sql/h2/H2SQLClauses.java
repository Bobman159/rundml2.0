package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.core.sql.ISQLEnum;

/**
 * Defines enumerations for SQL clauses specific to H2 databases for SELECT, INSERT, UPDATE,
 * and DELETE statements.  
 * 
 * This class is not intended to be sub classed.
 *
 */
public class H2SQLClauses  {

	/**
	 * Define H2 DBMS specifc SQL clauses.  Clauses are defined in alphabetical order.	 *
	 */
	public enum H2SelectClause implements ISQLEnum {
		LIMIT("limit"),
		OFFSET("offset"),
		TOP("top");
		
		private String clause = "";
		
		private H2SelectClause(String clause) {
			this.clause = clause;
		}
		
		/**
		 * Return the SQL clause enumeration as a text string.
		 */
		public String serializeClause() {
			return this.clause;
		}
		
		
		
	}

}
