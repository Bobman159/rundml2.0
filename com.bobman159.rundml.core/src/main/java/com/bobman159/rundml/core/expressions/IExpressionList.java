package com.bobman159.rundml.core.expressions;

import java.util.List;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * A list of expression clauses
 *
 */
public interface IExpressionList {

	/**
	 * Add a list of expressions to the list of expressions being tracked 
	 * by this class.
	 * 
	 * @param expressionList expressions list of expressions
	 */
	public void addExpressions(ISQLType[] expressionList);
	
	/**
	 * Add an expression to list of expressions being tracked by this class
	 * @param expresion the expression to be added
	 */
	public void addExpression(ISQLType expresion);
	
	/**
	 * 
	 * @return the expressions list as a unmodifiable list
	 */
	public List<ISQLType> getExpressions();
}
