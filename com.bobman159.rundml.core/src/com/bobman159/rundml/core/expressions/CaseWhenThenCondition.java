package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;

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
	 * Define a WHEN <code>sqlCondition</code> THEN <code>expr</code> 
	 * @param when the WHEN SQL condition (a &gt; b)
	 * @param then the THEN SQL expression
	 */
	public CaseWhenThenCondition(SQLCondition when, IExpression then) {
		this.when = when;
		this.then = then;
	}
	
	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		
		String expr = "";
		expr = expr + " when " + when.serialize();
		expr = expr + " then " + then.serialize();
		
		return expr;
	}
}