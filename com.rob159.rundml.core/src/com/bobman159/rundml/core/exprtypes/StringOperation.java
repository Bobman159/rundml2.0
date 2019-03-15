package com.bobman159.rundml.core.exprtypes;

import com.bobman159.rundml.core.sql.sql.conditions.Op;

/**
 * Define a string operation between two SQL expressions, strings, columns,
 * case statements etc. 
 *
 */
public class StringOperation implements IExpression, IStringOperations {
	
	private IExpression lhs;
	private IExpression rhs;
	private Op operator;
	
	/**
	 * Define a string operation between two SQL expressions
	 * @param lhs - left side of the operation
	 * @param rhs - right side of the operation
	 * @param op - the operation
	 */
	public <T extends IExpression> StringOperation(T lhs, T rhs, Op op) {
		this.lhs = lhs;
		this.rhs = rhs;
		operator = op;
	}
	
	/**
	 * @see com.bobman159.rundml.core.exprtypes.IExpression#serialize()
	 */
	@Override
	public String serialize() {
		String expression = "";
		
		expression = lhs.serialize() + operator.getOperator() + rhs.serialize();
		return expression;
	}

}
