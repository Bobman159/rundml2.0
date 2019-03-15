package com.bobman159.rundml.core.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
	 * Add an expression to list of expressions being tracked by this class
	 * @param expresion - the expression to be added
	 */
	public void addExpression(IExpression expresion) {
		expressions.add(expresion);
	}
	
	/**
	 * @see java.lang.Iterable#forEach(Consumer)
	 */
	public void forEach(Consumer<? super IExpression> action) {
		expressions.forEach(action);
	}

}
