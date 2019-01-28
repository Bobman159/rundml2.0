package net.bobs.own.db.rundml.sql.expression.types;

import net.bobs.own.db.rundml.sql.condition.Op;

/**
 * Defines math operations for SQL expressions.  Default implementations are
 * provided for the methods to implement a mixin style implementation for the 
 * math operations.
 * 
 *
 */
public interface IMathOperations {
	
	/**
	 * Adds an SQL Expression on the left to the one on the right.
	 * @param rhs - the expression on the right of the add
	 * @return - the add expression
	 */
	public default NumericOperation add(Number number) {
		return ExprTypeHelper.mathOperation((IExpression) this, number,Op.ADD);
	}
	
	
	/**
	 * Adds an SQL Expression on the left to the one on the right.
	 * @param rhs - the expression on the right of the add
	 * @return - the add expression
	 */
	public default NumericOperation add(IExpression expr) {
		return ExprTypeHelper.mathOperation((IExpression) this, expr, Op.ADD);
	}
	
	/**
	 * Subtracts an SQL Expression on the right from the one on the left.
	 * @param rhs - the expression on the right of the subtraction
	 * @return - the subtract expression
	 */
	
	public default NumericOperation subtract(Number number) {
		return ExprTypeHelper.mathOperation((IExpression) this, number,Op.SUB);
	}
	
	/**
	 * Subtract an SQL Expression on the right from the one on the left.
	 * @param rhs - the expression on the right of the subtraction
	 * @return - the subtract expression
	 */
	public default NumericOperation subtract(IExpression expr) {
		return ExprTypeHelper.mathOperation((IExpression) this, expr, Op.SUB);
	}
	
	/**
	 * Multiplies an SQL Expression on the left to the one on the right.
	 * @param rhs - the expression on the right of the multiply
	 * @return - the multiplication expression
	 */
	public default NumericOperation multiply(Number number) {
		return ExprTypeHelper.mathOperation((IExpression) this, number,Op.MULT);
	}
	
	/**
	 * Multiplies an SQL Expression on the left to the one on the right.
	 * @param rhs - the expression on the right of the multiply
	 * @return - the multiplication expression
	 */
	public default NumericOperation multiply(IExpression expr) {
		return ExprTypeHelper.mathOperation((IExpression) this, expr, Op.MULT);
	}
	
	/**
	 * Divides an SQL Expression on the right from the one on the left.
	 * @param rhs - the expression on the right of the divide
	 * @return - the division expression
	 */
	
	public default NumericOperation divide(Number number) {
		return ExprTypeHelper.mathOperation((IExpression) this, number,Op.DIVIDE);
	}
	
	/**
	 * Divides an SQL Expression on the right from the one on the left.
	 * @param rhs - the expression on the right of the divide
	 * @return - the add expression
	 */
	public default NumericOperation divide(IExpression expr) {
		return ExprTypeHelper.mathOperation((IExpression) this, expr, Op.DIVIDE);
	}

}
