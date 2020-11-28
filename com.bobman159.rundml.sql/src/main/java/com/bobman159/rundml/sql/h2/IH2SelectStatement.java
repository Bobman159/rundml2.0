package com.bobman159.rundml.sql.h2;

import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * H2 database SELECT statement definition
 * 
 */
public interface IH2SelectStatement extends ISelectStatement {
	
	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param topNumber an integer that limits the number of rows returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.types.IExpression
	 * 
	 */
	public void addTop(int topNumber);
	
	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param topExpr an expression that limits the number of rows returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.types.IExpression
	 * 
	 */
	public void addTop(ISQLType topExpr);
	
	/**
	 * @return the TOP clause for the H2 SELECT statement
	 */
	public ISQLType getTop();
	
	/**
	 * Specify a LIMIT clause for the SELECT statement
	 * @param limitTerm an expression to limit the number of results returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.types.IExpression
	 * 
	 */

	public void addLimit(ISQLType limitTerm);
	
	/**
	 * 
	 * @return the LIMIT clause definition for the H2 SELECT statement
	 */
	public ISQLType getLimit();
	
	/**
	 * Specify an OFFSET clause for the LIMIT clause in a SELECT
	 * statement.
	 * @param offset specifies how many rows to skip
	 * @return the SELECT statement builder
	 */
	public  void addOffset(ISQLType offset);

	/**
	 * 
	 * @return the OFFSET clause definition for the H2 SELECT statement
	 */
	public ISQLType getOffset();
	
}
