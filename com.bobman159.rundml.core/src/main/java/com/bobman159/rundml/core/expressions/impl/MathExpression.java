package com.bobman159.rundml.core.expressions.impl;

import com.bobman159.rundml.core.expressions.AbstractBaseExpression;
import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;

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
	public IMathExpression add(Number number) {

		return (IMathExpression) addToExpression(this,Op.ADD,SQLTypeFactory.getInstance().constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#add(ISQLType)
	 */
	@Override
	public IMathExpression add(ISQLType expr) {
		return (IMathExpression) addToExpression(this,Op.ADD,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#subtract(Number)
	 */
	@Override
	public IMathExpression subtract(Number number) {
		return (IMathExpression) addToExpression(this,Op.SUB,SQLTypeFactory.getInstance().constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#subtract(ISQLType)
	 */
	@Override
	public IMathExpression subtract(ISQLType expr) {	
		return (IMathExpression) addToExpression(this,Op.SUB,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#multiply(Number)
	 */
	@Override
	public IMathExpression multiply(Number number) {
		return (IMathExpression) addToExpression(this,Op.MULT,SQLTypeFactory.getInstance().constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#multiply(ISQLType)
	 */
	@Override
	public IMathExpression multiply(ISQLType expr) {
		return (IMathExpression) addToExpression(this,Op.MULT,expr);
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#divide(Number)
	 */
	@Override
	public IMathExpression divide(Number number) {
		return (IMathExpression) addToExpression(this,Op.DIVIDE,SQLTypeFactory.getInstance().constant(number));
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IMathExpression#divide(ISQLType)
	 */
	@Override
	public IMathExpression divide(ISQLType expr) {
		return (IMathExpression) addToExpression(this,Op.DIVIDE,expr);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.AbstractBaseExpression#createExpressionNode(ISQLType, Op, ISQLType)
	 */
	@Override
	public IExpressionNode createExpressionNode(ISQLType leftExpr, Op operator, ISQLType rightExpr) {
		return new MathExpression(leftExpr,operator,rightExpr);
	}

}
