package net.bobs.own.db.rundml.sql.expressions;

import net.bobs.own.db.rundml.sql.condition.SQLCondition;
import net.bobs.own.db.rundml.sql.expression.types.IExpression;

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