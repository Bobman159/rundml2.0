package com.bobman159.rundml.core.sql;

import java.util.List;

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
public interface ICaseClause extends ISQLClause {

	/**
	 * 
	 * @return the CASE expression type value or  expression node for CASE expression statement
	 */
	public ISQLType getCaseExpr();
	
	/**
	 * Add a WHEN value THEN value 
	 * @param whenValue the sql condition or value for WHEN clause
	 * @param thenValue the sql condition or value for THEN clause
	 */
	public void setWhenThen(ICaseWhenValue whenValue,ISQLType thenValue);
	
	/**
	 * Get the list WHEN expression THEN expression condition clauses for the SQL CASE statement as a 
	 * unmodifiable list
	 * @return the list of WHEN conditions for the CASE
	 */
	public List<ICaseWhenThen> getWhenThenConditions();
	
	/**
	 * 
	 * @return true if the CASE has an ELSE expression, false otherwise
	 */
	public boolean hasElse();
	
	/**
	 * Add an ELSE expression condition for the CASE
	 * @param elseExpr the sql value or condition for the ELSE
	 */
	public void setElse(ISQLType elseExpr);
	
	/**
	 * @return the ELSE condition for this CASE statement
	 */
	public ISQLType getElse();

	/**
	 * 
	 * @return true if this is a CASE WHEN statement , false if this is a CASE WHEN clause
	 */
	public boolean isCaseWhen();
		
}
