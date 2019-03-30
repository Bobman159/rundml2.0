package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;

/**
 * Defines a single "WHEN IExpressionType THEN expression for a CASE or CASE WHEN  SQL expression.
 * 
 * @author Robert Anderson
 *
 */
public class CaseWhenThenCondition implements IExpression {
	IExpression when;
	IExpression then;
	
	public CaseWhenThenCondition(IExpression when, IExpression then) {
		this.when = when;
		this.then = then;
	}
	
	public CaseWhenThenCondition(SQLCondition when, IExpression then) {
		this.when = when;
		this.then = then;
	}
	
	@Override
	public String serialize() {
		
		String expr = "";
		expr = expr + " when " + when.serialize();
		expr = expr + " then " + then.serialize();
		
		return expr;
	}
}