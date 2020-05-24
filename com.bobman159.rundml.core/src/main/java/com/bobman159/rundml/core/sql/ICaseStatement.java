package com.bobman159.rundml.core.sql;

import java.util.stream.Stream;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * An SQL CASE statement.  
 * <ul>
 * 	<li>CASE expression WHEN expression THEN expression [ELSE expression] END</li>
 * 	<li>CASE WHEN expression THEN expression [ELSE expression] END are supported</li>
 * </ul>
 *
 *	<p>are supported</p>
 */
public interface ICaseStatement extends ISQLClause {
	
	/**
	 * @return the type of SQL clause
	 */
	public ISQLClause getType();
	
	/**
	 * 
	 * @return the CASE expression type value or  expression node for CASE expression statement
	 */
	public ISQLType getCaseExpr();
	
	/**
	 * Get the list WHEN expression THEN expression condition clauses for the SQL CASE statement as a 
	 * <code>Stream</code>
	 * @return the list of WHEN conditions for the CASE
	 */
	public Stream<ICaseWhenConditions> getWhenConditions();
	
	/**
	 * 
	 * @return true if the CASE has an ELSE expression, false otherwise
	 */
	public boolean hasElse();

	/**
	 * 
	 * @return true if this is a CASE WHEN expression clause, false if this is a CASE expession WHEN expression clause
	 */
	public boolean isCaseWhen();
	
	/**
	 * @return the ELSE condition for this CASE statement
	 */
	public ISQLType getElse();
	
	
	
}
