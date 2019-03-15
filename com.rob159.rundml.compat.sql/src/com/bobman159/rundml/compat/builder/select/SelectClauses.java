package com.bobman159.rundml.compat.builder.select;

import com.bobman159.rundml.compat.model.ISQLEnum;

public class SelectClauses {
	
	public enum SelectClause implements ISQLEnum  {
		SELECT("select"),
		SELECTSTAR(" *"),
		SELECTEXPR(" "),
		ALL(" all"),
		DISTINCT(" distinct"),
		FROM(" from"),
		WHERE(" where"),
		ORDERBY(" order by"),
		GROUPBY(" group by"),
		HAVING(" having");
		
		String clause = "";
		
		private SelectClause(String clause) {
			this.clause = clause;
		}
		
		public String serializeClause() {
			return clause;
		}
		
	}

}
