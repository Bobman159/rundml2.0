package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.expressions.IStringExpression;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * SQL Types Factory Interface
 *
 */
public interface ISQLTypeFactory {

	/**
	 * Create a SQL column name
	 * @param columnName name of the colum
	 * @return an SQL column
	 */
	public ISQLType column(String columnName);
	
	/**
	 * Create a numeric value expression from a java primitive type.
	 * Integer, double, etc.
	 * 
	 * @param numb the number value
	 * @return a <code>NumericType</code> expression value
	 */
	public ISQLType constant(Number numb);

	/**
	 * Create a string value expression type from a java primitive type 
	 * @param value the string value
	 * @return a <code>StringType</code> expression value
	 */
	public ISQLType constant(String value);
	
	/**
	 * Crate unqualified database table value type 
	 * @param tableName the database table name 
	 * @return a <code>Table</code> type
	 */
	public ISQLType unQualifiedTable(String tableName); 
	
	/**
	 * Create a qualified (schema.tableName) database table value type
	 * @param schema the database schema for the table
	 * @param tableName the database table name
	 * @return a ISQLType for a fully qualified (schema.tableName) table
	 */
	public ISQLType qualifiedTable(String schema, String tableName);
	
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
	public ISQLType parm(int jdbcType,Object value);

	//Expressions
	/**
	 * Create an SQL string expression from a java String.
	 * The string value is enclosed in single quotes (') automatically.
	 * 
	 * @param value the string value
	 * @return a string expression
	 */
	public IStringExpression stringExpression(String value);
	
	/**
	 * Create an SQL string expression using a exprssion value (Column, parameter marker etc)
	 * @param firstExpr the first part of the expression
	 * @return a string expression
	 */
	public IStringExpression stringExpression(ISQLType firstExpr);
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a java number value 
	 * (float, int, short etc)
	 * @param value the java number type
	 * @return a mathematical expression
	 */
	public IMathExpression mathExpression(Number value);
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a expression
	 * value (column, parameter marker etc)
	 * @param value an expression type value
	 * @return a mathematical expression
	 */
	public IMathExpression mathExpression(ISQLType value);
	
	//CASE
	/**
	 * Build a CASE WHEN value statement
	 * @return a CASE statement builder
	 */
	public ICaseBuilder caseClause();
	
	/**
	 * Build a CASE value statement
	 * @param caseValue value for the CASE clause
	 * @return a CASE statement builder 
	 */
	public ICaseBuilder caseClause(ISQLType caseValue);
	
	/**
	 * Build a CASE math expression statement
	 * @param mathExpr math expression for the CASE clause
	 * @return a CASE statement builder 
	 */
	public ICaseBuilder caseClause(IExpressionNode mathExpr);
	
	/**
	 * Create an ORDER BY number clause entry IE ORDER BY 1 
	 * @param position number argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderBy(int position);
	
	/**
	 * Create an ORDER BY sql type clause entry IE ORDER BY COL01
	 * @param expr SQL column argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderBy(ISQLType expr);
	
	/**
	 * Create an ORDER BY expression clause entry IE ORDER BY 10 + 10 
	 * @param expr SQL expression argument
	 * @return an <code>OrderByEntry</code>
	 */
	public IOrderByEntry orderBy(IExpressionNode expr);
}
