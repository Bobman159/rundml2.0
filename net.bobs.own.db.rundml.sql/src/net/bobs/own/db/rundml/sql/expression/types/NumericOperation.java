package net.bobs.own.db.rundml.sql.expression.types;

import net.bobs.own.db.rundml.sql.condition.Op;

/**
 * Define a numeric operation between two SQL Expressions, numbers,
 * columns, case statements etc.
 *
 */

public class NumericOperation implements IExpression, IMathOperations {

	private IExpression lhs;
	private IExpression rhs;
	private Op operator;
	 	
	/**
	 * Define a numeric operation between a SQL Expression and an SQL Expression
	 * @param lhs - left side of the operation
	 * @param rhs - right side of the operation
	 * @param op - the operation
	 */
	public <T extends IExpression> NumericOperation(T lhs, T rhs, Op op) {
		this.lhs = lhs;
		this.rhs = rhs;
		operator = op;
	}
	
	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		String expression = "";
		
		expression = lhs.serialize() + operator.getOperator() + rhs.serialize() ;
		return expression;

	}

}
