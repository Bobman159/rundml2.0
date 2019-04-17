package com.bobman159.rundml.core.sql;

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
	 * Define non DBMS specifc SQL clauses.  Clauses are defined in alphabetical order.	 *
	 */
	public enum SQLClause implements ISQLEnum  {
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
		WHERE("where");
		
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
