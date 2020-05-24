package com.bobman159.rundml.core.expressions;

import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.impl.CaseClause;
import com.bobman159.rundml.core.sql.impl.CaseWhenClause;
import com.bobman159.rundml.core.sql.impl.OrderByExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.core.sql.types.impl.StringType;
import com.bobman159.rundml.core.sql.types.impl.Table;

/** 
 * Factory class for creating SQL Expression types. 
 *
 */
public class Expression {
	
	private static Expression self;
	
	public static Expression getInstance() {
		if (self == null) {
			self = new Expression();
		}
		return self;
	}

	private Expression() {
		throw new IllegalStateException("Expression is a factory class");
	}
	
	/**
	 * Create a column expression defining a column name in a database table
	 * @param columnName string column name in table
	 * @return a <code>Column</code> expression
	 */
	public static ISQLType column(String columnName) {
		return new Column(columnName);
	}
	
	/**
	 * Create a numeric value expression from a java primitive type.
	 * Integer, double, etc.
	 * 
	 * @param numb the number value
	 * @return a <code>NumericType</code> expression value
	 */
	public static NumericType constant(Number numb) {
		return new NumericType(numb);
	}

	/**
	 * Create a string value expression type from a java primitive type 
	 * @param value the string value
	 * @return a <code>StringType</code> expression value
	 */
	public static ISQLType constant(String value) {
		return new StringType(value);
	}
	
	/**
	 * Crate unqualified database table value type 
	 * @param tableName the database table name 
	 * @return a <code>Table</code> type
	 */
	public static Table unQualifiedTable(String tableName) {
		return new Table(tableName);
	}
	
	/**
	 * Create a qualified (schema.tableName) database table value type
	 * @param schema the database schema for the table
	 * @param tableName the database table name
	 */
	public static Table qualifiedTable(String schema, String tableName) {
		return new Table(schema, tableName);
	}
	
	/**
	 * Create an SQL string expression from a java String.
	 * The string value is enclosed in single quotes (') automatically.
	 * 
	 * @param value the string value
	 * @return a string expression
	 */
	public static StringExpression stringExpression(String value) {
		return new StringExpression(value);
	}
	
	/**
	 * Create an SQL string expression using a exprssion value (Column, parameter marker etc)
	 * @param firstExpr the first part of the expression
	 * @return a string expression
	 */
	public static StringExpression stringExpression(ISQLType firstExpr) {
		return new StringExpression(firstExpr);
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a java number value 
	 * (float, int, short etc)
	 * @param value the java number type
	 * @return a mathematical expression
	 */
	public static MathExpression mathExpression(Number value) {
		return new MathExpression(Expression.constant(value));
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a expression
	 * value (column, parameter marker etc)
	 * @param value an expression type value
	 * @return a mathematical expression
	 */
	public static MathExpression mathExpression(ISQLType value) {
		return new MathExpression(value);
	}
	
	/**
	 * Create a SQL parameter marker expression for the SQL statement.
	 * A "?" will be generated in the SQL statement for each parameter marker.
	 * 
	 * @param jdbcType the JDBC type for the parameter marker
	 * @param value the data value for the parameter marker.
	 * 
	 * @see java.sql.Types
	 * @return a <code>ParmMarker</code> expression 
	 */
	public static ParmMarker parm(int jdbcType,Object value) {
		return new ParmMarker(jdbcType,value);
	}
		
	/**
	 * Create a case expression 
	 * @param expr expression for the CASE statement
	 * @return a CASE expression
	 */
	public static CaseClause caseExpr(ISQLType expr) {
		return new CaseClause(expr);
	}
	
	/** Create a case when expression
	 * @return a CASE WHEN expression
	 */
	public static CaseWhenClause caseWhen() {
		return new CaseWhenClause();
	}
	
	/**
	 * Creates a numeric (1 2 etc) ORDER BY clause expression
	 * @param orderByExpr numeric number for ORDER BY clause
	 * @return SQL ORDER BY expression 
	 */
	public static OrderByExpression orderBy(int orderByExpr) {

		ISQLType iExpr = Expression.constant(orderByExpr);
		return new OrderByExpression(iExpr);
	}
	
	/**
	 * Creates an <code>ISQLType</code> (column, number, string) 
	 * based ORDER BY expression
	 * @param orderByExpr the SQL expresion for the ORDER BY
	 * @return SQL ORDER BY expression
	 */
	public static OrderByExpression orderBy(ISQLType orderByExpr) {
		return new OrderByExpression(orderByExpr);
	}

}
