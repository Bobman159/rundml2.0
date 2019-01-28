package net.bobs.own.db.rundml.sql.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.bobs.own.db.rundml.sql.expression.types.IExpression;

/**
 * Maintains a <code>List</code> of <code>IExpressionType</code> objects
 * for use in SQL select, insert, or update statements
 */

public class ExpressionList {
	
	private List<IExpression> expressions;
	
	
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
