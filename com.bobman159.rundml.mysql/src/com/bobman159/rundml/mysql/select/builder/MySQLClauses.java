package com.bobman159.rundml.mysql.select.builder;

import com.bobman159.rundml.core.sql.ISQLEnum;

public class MySQLClauses {

	public enum MySQLClause implements ISQLEnum {
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
