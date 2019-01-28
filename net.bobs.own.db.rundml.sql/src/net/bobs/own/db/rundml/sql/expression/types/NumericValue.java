package net.bobs.own.db.rundml.sql.expression.types;

/**
 * Define a numeric value or literal that is specified an SQL statement.
 * 
 * @author Robert Anderson
 *
 */
public class NumericValue implements IExpression, IMathOperations {
	private Number number;
	
	/**
	 * Create a numeric value expression from a java primitive value
	 * (int,short, double etc).
	 * 
	 * @param lhs - java primitive type value
	 */
	public NumericValue(Number lhs) {
		this.number = lhs;
	}
	
	/**
	 * @see net.bobs.own.db.rundml.sql.expression.types.IExpression#serialize()
	 */

	@Override
	public String serialize() {
		String expression = "";
	
		expression = expression + number.toString();

		return expression;
	}
	
}
