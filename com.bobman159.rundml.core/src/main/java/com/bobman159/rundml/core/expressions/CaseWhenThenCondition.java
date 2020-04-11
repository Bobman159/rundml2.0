package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.exprtypes.IExpression;

/**
 * Defines a single "WHEN IExpressionType THEN expression for a CASE or CASE WHEN  SQL expression.
 *
 */
public class CaseWhenThenCondition implements IExpression {
	IExpression when;
	IExpression then;
	
	/**
	 * Define the WHEN <code>expr</code> THEN <code>expr</code> condition using an SQL expression 
	 * @param when the WHEN SQL expression
	 * @param then the THEN SQL expression
	 */
	public CaseWhenThenCondition(IExpression when, IExpression then) {
		this.when = when;
		this.then = then;
	}
	
	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		
		StringBuilder expr = new StringBuilder();
//		expr.append(" ").append("when").append(" ").append(when.serialize());
		expr.append("when ").append(when.serialize());
		doAppendSpace(expr);
		expr.append("then").append(" ").append(then.serialize());
		doAppendSpace(expr);
		
		return expr.toString();
	}
	
	//Appends a space to the expression if there is not one there already
	private void doAppendSpace(StringBuilder expr) {
		if (expr.charAt(expr.length() - 1) != ' ') {
			expr.append(' ');
		}
	}
}