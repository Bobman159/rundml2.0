package net.bobs.own.db.rundml.sql.builders.exprs;

import net.bobs.own.db.rundml.sql.expression.types.ExprString;
import net.bobs.own.db.rundml.sql.expression.types.IExpression;
import net.bobs.own.db.rundml.sql.expression.types.NumericValue;
import net.bobs.own.db.rundml.sql.expression.types.ParmMarker;
import net.bobs.own.db.rundml.sql.expressions.CaseExpression;
import net.bobs.own.db.rundml.sql.expressions.CaseWhenExpression;

/** 
 * Factory class for creating SQL Expression types. 
 *
 */
public class Expression {

	private Expression() {
		throw new IllegalStateException("Expression is a factory class");
	}
	
	/**
	 * Create a numeric value expression from a java primitive type.
	 * Integer, double, etc.
	 * 
	 * @param numb - the number value
	 * @return - a <code>NumericValue</code> expression
	 */
	public static NumericValue number(Number numb) {
		return new NumericValue(numb);
	}
	
	/**
	 * Create an SQL string expression from a java String.
	 * The string value is enclosed in single quotes (') automatically.
	 * 
	 * @param value - the string value
	 * @return - a <code>SQLString</code> expression
	 */
	public static ExprString string(String value) {
		return new ExprString(value);
	}
	
	/**
	 * Create a SQL parameter marker expression for the SQL statement.
	 * A "?" will be generated in the SQL statement for each parameter marker.
	 * 
	 * @param jdbcType - the JDBC type for the parameter marker
	 * @param value - the data value for the parameter marker.
	 * 
	 * @see java.sql.Types
	 * @return - a <code>ParmMarker</code> expression 
	 */
	public static ParmMarker parm(int jdbcType,Object value) {
		return new ParmMarker(jdbcType,value);
	}
		
	/**
	 * Create a case expression 
	 * @param expr - expression for the CASE statement
	 * @return - a CASE expression
	 */
	public static CaseExpression caseExpr(IExpression expr) {
		return new CaseExpression(expr);
	}
	
	/** Create a case when expression
	 * 
	 * @return - a CASE WHEN expression
	 */
	public static CaseWhenExpression caseWhen() {
		return new CaseWhenExpression();
	}
			
}
