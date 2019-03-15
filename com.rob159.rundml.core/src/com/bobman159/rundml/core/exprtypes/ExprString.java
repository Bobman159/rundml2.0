package com.bobman159.rundml.core.exprtypes;

/**
 * Define a String expression that is specified in an SQL statement
 *
 */
public class ExprString implements IExpression, IStringOperations {

	private String stringExpr;
	
	/**
	 * Defines an SQL String expression, the <code>stringExpr</code> is 
	 * automatically escaped with "\'" at the start and end of the string.
	 * 
	 * @param stringExpr - the escaped SQL string expression
	 */
	public ExprString(String stringExpr) {
		this.stringExpr = String.format("\'%s\'", stringExpr);
	}
	
	@Override
	public String serialize() {

		return stringExpr;
	}

}
