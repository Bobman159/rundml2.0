package com.bobman159.rundml.core.expressions.impl;

import com.bobman159.rundml.core.expressions.AbstractBaseExpression;
import com.bobman159.rundml.core.expressions.IExpressionFactory;
import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IStringExpression;
import com.bobman159.rundml.core.sql.sql.conditions.Op;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.ISQLTypesFactory;

/**
 * An SQL String expression for an SQL statement clause.
 * @author Robert Anderson
 *
 */
public class StringExpression extends AbstractBaseExpression implements IExpressionNode, IStringExpression {

	/**
	 * Define an SQL string expression between two strings
	 * @param firstString the first string in the expression
	 */
	public StringExpression(String firstString) {
		super(new ISQLTypesFactory() {}.constant(firstString));
	}
	
	/**
	 * Define an SQL string expression between two strings or string types
	 * @param firstExpr the first string expression (column, parm marker etc) in the in the expression
	 */
	public StringExpression(ISQLType firstExpr) {
		super(firstExpr);
	}
	
	/*
	 * Create an SQL string expression  
	 * @param leftExpr expression on the left of the operator
	 * @param operator the string operation
	 * @param rightExpr expression on the right of the operator
	 */

	private StringExpression(ISQLType leftExpr,Op operator,ISQLType rightExpr) {		
		super(leftExpr,operator, rightExpr);
	}
	
	/**
	 * @see com.bobman159.rundml.core.expressions.IStringExpression#concat(String)
	 */
	@Override
	public StringExpression concat(String secondString) {
		 return (StringExpression) addToExpression(this,Op.CONCAT,new ISQLTypesFactory() {}.constant(secondString));
	
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.IStringExpression#concat(ISQLType)
	 */
	@Override
	public StringExpression concat(ISQLType rhs) {
		return (StringExpression) addToExpression(this, Op.CONCAT, rhs);
	}

	/**
	 * @see com.bobman159.rundml.core.expressions.AbstractBaseExpression#createExpressionNode(ISQLType, Op, ISQLType)
	 */
	@Override
	public IExpressionNode createExpressionNode(ISQLType leftExpr, Op operator, ISQLType rightSide) {		
		return new StringExpression(leftExpr, operator,rightSide);
	}

}
