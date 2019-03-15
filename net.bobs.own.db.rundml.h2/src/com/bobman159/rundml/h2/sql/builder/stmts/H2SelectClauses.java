package com.bobman159.rundml.h2.sql.builder.stmts;

import com.bobman159.rundml.compat.model.ISQLEnum;

public class H2SelectClauses  {

	public enum H2SelectClause implements ISQLEnum {
		TOP(" top "),
		LIMIT(" limit ");
		
		private String clause = "";
		
		private H2SelectClause(String clause) {
			this.clause = clause;
		}
		
		public String serializeClause() {
			return this.clause;
		}
		
		
		
	}

}
