package com.bobman159.rundml.core.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bobman159.rundml.core.exprtypes.IExpression;

/**
 * Maintains a <code>List</code> of <code>IExpressionType</code> objects
 * for use in SQL select, insert, or update statements
 */

public class ExpressionList {
	
	private List<IExpression> expressions;
	
	/**
	 * Define the list for <code>IExpression</code> types.
	 */
	public ExpressionList() {
		expressions = new ArrayList<>();
	}
	
	/**
	 * Add a list of expressions to the list of expressions being tracked 
	 * by this class.
	 * 
	 * @param expressionList expressions list of expressions
	 */
	public void addExpressions(IExpression[] expressionList) {
		for (IExpression expr : expressionList) {
			this.expressions.add(expr);
		}
	}
	
	/**
	 * Add an expression to list of expressions being tracked by this class
	 * @param expresion the expression to be added
	 */
	public void addExpression(IExpression expresion) {
		expressions.add(expresion);
	}
	
	/**
	 * Returns a list of SQL expressions as comma separated text 
	 * "expr1,expr2,expr3..."
	 * 
	 * @return comma separated text string
	 */
	public String toCSV() {
	
		String csvString = "";
		csvString = expressions.stream()
			.map(IExpression::serialize)
            .collect( Collectors.joining(",") );
		return csvString;
	}

}
