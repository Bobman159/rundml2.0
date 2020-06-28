package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.ISQLTypesFactory;

	
/** 
 * Factory interface for creating SQL Types. 
 *
 */
public interface IExpressionFactory {

	/**
	 * Create an SQL string expression from a java String.
	 * The string value is enclosed in single quotes (') automatically.
	 * 
	 * @param value the string value
	 * @return a string expression
	 */
	public default StringExpression stringExpression(String value) {
		return new StringExpression(value);
	}
	
	/**
	 * Create an SQL string expression using a exprssion value (Column, parameter marker etc)
	 * @param firstExpr the first part of the expression
	 * @return a string expression
	 */
	public default StringExpression stringExpression(ISQLType firstExpr) {
		return new StringExpression(firstExpr);
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a java number value 
	 * (float, int, short etc)
	 * @param value the java number type
	 * @return a mathematical expression
	 */
	public default MathExpression mathExpression(Number value) {
		new ISQLTypesFactory() {}.constant(value);
		return new MathExpression(new ISQLTypesFactory() {}.constant(value));
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a expression
	 * value (column, parameter marker etc)
	 * @param value an expression type value
	 * @return a mathematical expression
	 */
	public default MathExpression mathExpression(ISQLType value) {
		return new MathExpression(value);
	}

}
