package com.bobman159.rundml.core.expressions.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Maintains a <code>List</code> of <code>ISQLType</code> objects
 * for use in SQL select, insert, or update statements
 */

public class ExpressionList implements IExpressionList {
	
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
	@Override
	public void addExpressions(ISQLType[] expressionList) {
		for (ISQLType expr : expressionList) {
			this.expressions.add(expr);
		}
	}
	
	/**
	 * Add an expression to list of expressions being tracked by this class
	 * @param expresion the expression to be added
	 */
	@Override
	public void addExpression(ISQLType expresion) {
		expressions.add(expresion);
	}
	
	/**
	 * 
	 * @return the expressions as a list of <code>ISQLType</code>
	 */
	@Override
	public List<ISQLType> getExpressions() {
		return Collections.unmodifiableList(expressions);
	}
	


}
