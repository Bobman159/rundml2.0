package com.bobman159.rundml.core.sql.types;

import com.bobman159.rundml.core.sql.impl.CaseClause;
import com.bobman159.rundml.core.sql.impl.CaseWhenClause;
import com.bobman159.rundml.core.sql.impl.OrderByExpression;

/**
 * Factory interface for creation of SQL clauses.
 *
 */
public interface ISQLClauseFactory {

	/**
	 * Create a case expression 
	 * @param expr expression for the CASE statement
	 * @return a CASE expression
	 */
	public default CaseClause caseExpr(ISQLType expr) {
		return new CaseClause(expr);
	}
	
	/** Create a case when expression
	 * @return a CASE WHEN expression
	 */
	public default CaseWhenClause caseWhen() {
		return new CaseWhenClause();
	}
	
	/**
	 * Creates a numeric (1 2 etc) ORDER BY clause expression
	 * @param orderByExpr numeric number for ORDER BY clause
	 * @return SQL ORDER BY expression 
	 */
	public default OrderByExpression orderBy(int orderByExpr) {

		ISQLType iExpr = new ISQLTypesFactory() {}.constant(orderByExpr);
		return new OrderByExpression(iExpr);
	}
	
	/**
	 * Creates an <code>ISQLType</code> (column, number, string) 
	 * based ORDER BY expression
	 * @param orderByExpr the SQL expresion for the ORDER BY
	 * @return SQL ORDER BY expression
	 */
	public default OrderByExpression orderBy(ISQLType orderByExpr) {
		return new OrderByExpression(orderByExpr);
	}
}
