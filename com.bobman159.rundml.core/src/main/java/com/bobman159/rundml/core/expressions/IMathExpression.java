package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * A mathematical expression for operations such as add, subtract, multiply and divide in 
 * an SQL expression.
 * 
 */
public interface IMathExpression {
	
	/**
	 * Adds an SQL Expression on the left to the one on the right.
	 * @param number the number on the right of the add
	 * @return the add expression
	 */
	public MathExpression add(Number number);

	/**
	 * Adds an SQL Expression on the left to the one on the right.
	 * @param expr the expression on the right of the add
	 * @return the add expression
	 */
	public MathExpression add(ISQLType expr);
	
	/**
	 * Subtracts an SQL Expression on the right from the one on the left.
	 * @param number the number on the right of the subtraction
	 * @return the subtract expression
	 */
	public MathExpression subtract(Number number);
	
	/**
	 * Subtract an SQL Expression on the right from the one on the left.
	 * @param expr the expression on the right of the subtraction
	 * @return the subtract expression
	 */
	public MathExpression subtract(ISQLType expr);
	
	/**
	 * Multiplies an SQL Expression on the left to the one on the right.
	 * @param number the number on the right of the multiply
	 * @return the multiplication expression
	 */
	public MathExpression multiply(Number number);
	
	/**
	 * Multiplies an SQL Expression on the left to the one on the right.
	 * @param expr the expression on the right of the multiply
	 * @return the multiplication expression
	 */
	public MathExpression multiply(ISQLType expr);
	
	/**
	 * Divides an SQL Expression on the right from the one on the left.
	 * @param number the number on the right of the divide
	 * @return the division expression
	 */
	public MathExpression divide(Number number);
	
	/**
	 * Divides an SQL Expression on the right from the one on the left.
	 * @param expr the expression on the right of the divide
	 * @return the division expression
	 */
	public MathExpression divide(ISQLType expr);

}
