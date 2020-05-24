package com.bobman159.rundml.core.expressions.impl;

import com.bobman159.rundml.core.expressions.AbstractBaseExpression;
import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * A math operation between two SQL Expressions, numbers,
 * columns, case statements etc.
 *
 */
public class MathExpression extends AbstractBaseExpression implements IMathExpression {

	/**
	 * Create an SQL expression 
	 * @param expr the expression to be created
	 */
	public MathExpression(ISQLType expr) {
		super(expr);
	}
	
	/*
	 * Create an SQL math expression
	 * @param leftExpr the value on the left of the math operator
	 * @param operator the math operator
	 * @param rightExpr the value on the right of the math operator
	 */
	private MathExpression(ISQLType leftExpr,Op operator, ISQLType rightExpr) {
		super(leftExpr,operator,rightExpr);
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#add(Number)
	 */
	@Override
	public MathExpression add(Number number) {

		return (MathExpression) addToExpression(this,Op.ADD,Expression.constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#add(ISQLType)
	 */
	@Override
	public MathExpression add(ISQLType expr) {
		return (MathExpression) addToExpression(this,Op.ADD,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#subtract(Number)
	 */
	@Override
	public MathExpression subtract(Number number) {
		return (MathExpression) addToExpression(this,Op.SUB,Expression.constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#subtract(ISQLType)
	 */
	@Override
	public MathExpression subtract(ISQLType expr) {	
		return (MathExpression) addToExpression(this,Op.SUB,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#multiply(Number)
	 */
	@Override
	public MathExpression multiply(Number number) {
		return (MathExpression) addToExpression(this,Op.MULT,Expression.constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#multiply(ISQLType)
	 */
	@Override
	public MathExpression multiply(ISQLType expr) {
		return (MathExpression) addToExpression(this,Op.MULT,expr);
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#divide(Number)
	 */
	@Override
	public MathExpression divide(Number number) {
		return (MathExpression) addToExpression(this,Op.DIVIDE,Expression.constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#divide(ISQLType)
	 */
	@Override
	public MathExpression divide(ISQLType expr) {
		return (MathExpression) addToExpression(this,Op.DIVIDE,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.AbstractBaseExpression#createExpressionNode(ISQLType, Op, ISQLType)
	 */
	@Override
	public IExpressionNode createExpressionNode(ISQLType leftExpr, Op operator, ISQLType rightExpr) {
		return new MathExpression(leftExpr,operator,rightExpr);
	}

}
