package com.bobman159.rundml.core.model;

import java.util.stream.Stream;

import com.bobman159.rundml.core.sql.ISQLEnum;
import com.bobman159.rundml.core.sql.SQLClauses.SQLClause;

/**
 * Serializes or creates an SQL Statement (SELECT, INSERT, UPDATE or DELETE) 
 * from an instance of an SQL statement model.
 *
 */
public abstract class SQLStatementSerializer { //NOSONAR
	
	/**
	 * Serialize an SQL SELECT statement from a model.
	 * @param model the model to build the SELECT statement from
	 * @return text of the SELECT statement
	 */
	public static String serializeSelect(SQLStatementModel model) {
		
		StringBuilder builder = new StringBuilder();		
		Stream<SQLModelNode> modelStream = model.sqlClauses();

		modelStream.forEach(modelNode  -> {
			ISQLEnum enumClause =  modelNode.getEnum();
			if (!enumClause.equals(SQLClause.SELECTEXPR) &&
				!enumClause.equals(SQLClause.WHERE) 	 &&
				!enumClause.equals(SQLClause.HAVING)) {
				builder.append(enumClause.serializeClause()).append(" ");
			}
				
			String arg = modelNode.argToString();
			if (arg != null) {
				builder.append(arg);
			}
		});	
		
		return builder.toString().trim();
	}
	
}
