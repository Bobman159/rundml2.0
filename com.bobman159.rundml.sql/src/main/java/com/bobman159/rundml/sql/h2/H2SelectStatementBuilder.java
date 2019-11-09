package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.h2.H2SQLClauses.H2SelectClause;

/**
 * Define a SELECT statement that can be executed against a table in an 
 * H2 database.
 *
 */
public class H2SelectStatementBuilder extends BaseSelectStatementBuilder<H2SelectStatementBuilder> {

	/**
	 * Create a SELECT statement that may be executed against an H2 database
	 * 
	 */
	public H2SelectStatementBuilder() {
		super();
	}		
	
	/**
	 * Specify a LIMIT clause for the SELECT statement
	 * @param limitTerm an expression to limit the number of results returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * 
	 */

	public H2SelectStatementBuilder limit(IExpression limitTerm) {
		model.addClause(H2SelectClause.LIMIT,limitTerm);
		return this;
	}
	
	/**
	 * Specify an OFFSET clause for the LIMIT clause in a SELECT
	 * statement.
	 * @param offset specifies how many rows to skip
	 * @return the SELECT statement builder
	 */
	public H2SelectStatementBuilder offset(IExpression offset) {
		model.addClause(H2SelectClause.OFFSET,offset);
		return this;
	}

	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param topExpr an expression that limits the number of rows returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * 
	 */
	public H2SelectStatementBuilder top(IExpression topExpr) {
		model.addClause(H2SelectClause.TOP,topExpr);
		return this;
	}
	
}
