package com.bobman159.rundml.core.expressions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.bobman159.rundml.core.sql.types.ISQLType;

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
	 * 
	 * @return the expressions list as a Stream<ISQLType>
	 */
	public Stream<ISQLType> stream() {
		return expressions.stream();
	}
	


}
