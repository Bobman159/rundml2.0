package com.bobman159.rundml.core.expressions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.bobman159.rundml.core.sql.impl.SQLClauseClient;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.util.CoreUtils;

/**
 * Maintains a <code>List</code> of <code>ISQLType</code> objects
 * for use in SQL select, insert, or update statements
 */

public class ExpressionList {
	
	private List<ISQLType> expressions;
	
	/**
	 * Define the list for <code>ISQLType</code> types.
	 */
	public ExpressionList() {
		expressions = new LinkedList<>();
	}
	
	/**
	 * Add a list of expressions to the list of expressions being tracked 
	 * by this class.
	 * 
	 * @param expressionList expressions list of expressions
	 */
	public void addExpressions(ISQLType[] expressionList) {
		for (ISQLType expr : expressionList) {
			this.expressions.add(expr);
		}
	}
	
	/**
	 * Add an expression to list of expressions being tracked by this class
	 * @param expresion the expression to be added
	 */
	public void addExpression(ISQLType expresion) {
		expressions.add(expresion);
	}
	
	/**
	 * Returns a list of SQL expressions as comma separated text 
	 * "expr1,expr2,expr3..."
	 * 
	 * @return comma separated text string
	 */
	public String toCSV() {
	
		StringBuilder csvString = new StringBuilder();
		Iterator<ISQLType> csvIterator = expressions.stream().iterator();
		while (csvIterator.hasNext()) {
			ISQLType exprBase = csvIterator.next();
			csvString.append(SQLClauseClient.getInstance().toSQLClause(exprBase));
			if (csvIterator.hasNext()) {
				csvString.append(",");
			}
		}
		
		return CoreUtils.normalizeString(csvString.toString());
	}

}
