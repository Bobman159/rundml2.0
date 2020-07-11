package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * A mathematical expression for operations such as add, subtract, multiply and divide in 
 * an SQL expression.
 * 
 */
public interface IMathExpression extends IExpressionNode {
	
	/**
	 * Adds an SQL IExpressionFactory on the left to the one on the right.
	 * @param number the number on the right of the add
	 * @return the add expression
	 */
	public IMathExpression add(Number number);

	/**
	 * Adds an SQL IExpressionFactory on the left to the one on the right.
	 * @param expr the expression on the right of the add
	 * @return the add expression
	 */
	public IMathExpression add(ISQLType expr);
	
	/**
	 * Subtracts an SQL IExpressionFactory on the right from the one on the left.
	 * @param number the number on the right of the subtraction
	 * @return the subtract expression
	 */
	public IMathExpression subtract(Number number);
	
	/**
	 * Subtract an SQL IExpressionFactory on the right from the one on the left.
	 * @param expr the expression on the right of the subtraction
	 * @return the subtract expression
	 */
	public IMathExpression subtract(ISQLType expr);
	
	/**
	 * Multiplies an SQL IExpressionFactory on the left to the one on the right.
	 * @param number the number on the right of the multiply
	 * @return the multiplication expression
	 */
	public IMathExpression multiply(Number number);
	
	/**
	 * Multiplies an SQL IExpressionFactory on the left to the one on the right.
	 * @param expr the expression on the right of the multiply
	 * @return the multiplication expression
	 */
	public IMathExpression multiply(ISQLType expr);
	
	/**
	 * Divides an SQL IExpressionFactory on the right from the one on the left.
	 * @param number the number on the right of the divide
	 * @return the division expression
	 */
	public IMathExpression divide(Number number);
	
	/**
	 * Divides an SQL IExpressionFactory on the right from the one on the left.
	 * @param expr the expression on the right of the divide
	 * @return the division expression
	 */
	public IMathExpression divide(ISQLType expr);

}
