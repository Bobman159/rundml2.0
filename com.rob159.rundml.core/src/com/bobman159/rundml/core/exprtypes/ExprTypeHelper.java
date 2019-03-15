package com.bobman159.rundml.core.exprtypes;

import com.bobman159.rundml.core.expressions.Expression;
import com.bobman159.rundml.core.sql.sql.conditions.Op;

/**
 * Helper class for SQL Expression operations. Provides convenience methods for 
 * defining SQL operations.
 *
 */
public class ExprTypeHelper {

	private ExprTypeHelper() {
		throw new IllegalStateException("ExprTypeHelper is a utility class");
	}
	/**
	 * Create a math operation, add subtract, multiply, divide between two 
	 * SQL expression types
	 * 
	 * @param lhs - an expression to the left of the math operator
	 * @param rhs - expression to the right of the math operator
	 * @param op - the operation (add, subtract, multiply, divide)
	 * @return - a <code>NumericOperation</code> for the expression
	 */
	public static NumericOperation mathOperation(IExpression lhs, Number rhs,Op op) {
		NumericValue valueRhs = new NumericValue(rhs);
		return new NumericOperation(lhs,valueRhs,op);	
	}
	
	/**
	 * Create a math operation, add, subtract, multiply, divide between two 
	 * SQL expression types
	 * @param lhs - expression to the left of the math operator
	 * @param rhs - expression to the right of the math operator
	 * @param op - the operation (add, subtract, multiply, divide)
	 * @return - <code>NumericOperation</code> for the expression
	 */
	public static NumericOperation mathOperation(IExpression lhs, IExpression rhs,Op op) {
		
		return new NumericOperation(lhs,rhs,op);
	}
	
	
	/**
	 * Create a string operation between two SQL expression types
	 * @param lhs - the expression to the left of the string operator
	 * @param rhs - the expression to the right of the string operator
	 * @param op - the string operation operator (concat, etc)
	 * @return
	 */
	public static StringOperation stringOperation(IExpression lhs, String rhs, Op op) {
		ExprString rhsExpr = new ExprString(rhs);
		return new StringOperation(lhs,rhsExpr,op);
	}
	
	/**
	 * Create a string operation between two SQL expression types
	 * @param lhs - the expression to the left of the string operator
	 * @param rhs - the expression to the right of the string operator
	 * @param op - the string operation operator (concat, etc)
	 * @return
	 */
	public static StringOperation stringOperation(IExpression lhs, IExpression rhs, Op op) {
		return new StringOperation(lhs,rhs,op);
	}
	
	/**
	 * Define the corresponding IExpression (Number or String) 
	 * from a Java Number or String object
	 * 
	 * @param expr - java object 
	 * @return - an corresponding <code>IExpression</code> from the input
	 */
	/*
	 * 
	 */
	public static IExpression convertExpression(Object expr) {
		
		IExpression iExpr = null;
		
		if (expr instanceof IExpression) {
			return (IExpression) expr;
		}
		
		if (expr instanceof Number) {
			Number exprNumb = (Number) expr;
			iExpr = Expression.number(exprNumb);
		} else if (expr instanceof String) {
			String exprString = (String) expr;
			iExpr = Expression.string(exprString);
		}
		
		return iExpr;
		
	}

}
