package com.bobman159.rundml.core.sql.types;

import com.bobman159.rundml.core.expressions.Expression;

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
	 * @param lhs an expression to the left of the math operator
	 * @param rhs expression to the right of the math operator
	 * @param op the operation (add, subtract, multiply, divide)
	 * @return a <code>NumericOperation</code> for the expression
	 */
//	public static NumericOperation mathOperation(ISQLType lhs, Number rhs,Op op) {
//		NumericValue valueRhs = new NumericValue(rhs);
//		return new NumericOperation(lhs,valueRhs,op);	
//	}
	
	/**
	 * Create a math operation, add, subtract, multiply, divide between two 
	 * SQL expression types
	 * @param lhs expression to the left of the math operator
	 * @param rhs expression to the right of the math operator
	 * @param op the operation (add, subtract, multiply, divide)
	 * @return <code>NumericOperation</code> for the expression
	 */
//	public static NumericOperation mathOperation(ISQLType lhs, ISQLType rhs,Op op) {
//		
//		return new NumericOperation(lhs,rhs,op);
//	}
	
	
	/**
	 * Create a string operation between two SQL expression types
	 * @param lhs the expression to the left of the string operator
	 * @param rhs the expression to the right of the string operator
	 * @param op the string operation operator (concat, etc)
	 * @return the SQL operation
	 */
//	public static StringOperation stringOperation(ISQLType lhs, String rhs, Op op) {
//		ExprString rhsExpr = new ExprString(rhs);
//		return new StringOperation(lhs,rhsExpr,op);
//	}
	
	/**
	 * Create a string operation between two SQL expression types
	 * @param lhs the expression to the left of the string operator
	 * @param rhs the expression to the right of the string operator
	 * @param op the string operation operator (concat, etc)
	 * @return the string operation expression
	 */
//	public static StringOperation stringOperation(ISQLType lhs, ISQLType rhs, Op op) {
//		return new StringOperation(lhs,rhs,op);
//	}
	
	/**
	 * Define the corresponding ISQLType (Number or String) 
	 * from a Java Number or String object
	 * 
	 * @param expr java object 
	 * @return a corresponding <code>ISQLType</code> from the input
	 */
	/*
	 * 
	 */
	public static ISQLType convertExpression(Object expr) {
		
		ISQLType iExpr = null;
		
		if (expr instanceof ISQLType) {
			return (ISQLType) expr;
		}
		
		if (expr instanceof Number) {
			Number exprNumb = (Number) expr;
			iExpr = Expression.constant(exprNumb);
		} else if (expr instanceof String) {
			String exprString = (String) expr;
			iExpr = Expression.constant(exprString);
		}
		
		return iExpr;
		
	}

}
