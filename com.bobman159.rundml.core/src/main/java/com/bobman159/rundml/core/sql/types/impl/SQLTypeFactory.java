package com.bobman159.rundml.core.sql.types.impl;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.expressions.IStringExpression;
import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.ICaseBuilder;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.ISQLTypeFactory;
import com.bobman159.rundml.core.sql.impl.CaseClauseBuilder;
import com.bobman159.rundml.core.sql.impl.OrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Factory class for the creation of SQL types 
 *
 */
public class SQLTypeFactory implements ISQLTypeFactory { 
	
	private static SQLTypeFactory self = null;
	
	/**
	 * @return an instance of the SQLTypeFactory
	 */
	 public static ISQLTypeFactory getInstance() {
		if (self == null) {
			self = new SQLTypeFactory();
		}
		return self;
	}
	
	public SQLTypeFactory() {
		//Default constructor
	}
	
	//Initially The Type factory methods were in an ITypesFactory interface,
	//the Expressions factory methods were in an IExpressionsFactory interface 
	//and the CASE factory methods were in an ICaseFactory interface 
	//all as default methods,  that seems unnecessary since this class can be 
	//sub-classed as needed thus one base class.

	/**
	 * Create a column expression defining a column name in a database table
	 * @param columnName string column name in table
	 * @return a <code>Column</code> expression
	 */
	@Override
	public ISQLType column(String columnName) {
		return new Column(columnName);
	}
	
	/**
	 * Create a numeric value expression from a java primitive type.
	 * Integer, double, etc.
	 * 
	 * @param numb the number value
	 * @return a <code>NumericType</code> expression value
	 */
	@Override
	public NumericType constant(Number numb) {
		return new NumericType(numb);
	}

	/**
	 * Create a string value expression type from a java primitive type 
	 * @param value the string value
	 * @return a <code>StringType</code> expression value
	 */
	@Override
	public ISQLType constant(String value) {
		return new StringType(value);
	}
	
	/**
	 * Crate unqualified database table value type 
	 * @param tableName the database table name 
	 * @return a <code>Table</code> type
	 */
	@Override
	public Table unQualifiedTable(String tableName) {
		return new Table(tableName);
	}
	
	/**
	 * Create a qualified (schema.tableName) database table value type
	 * @param schema the database schema for the table
	 * @param tableName the database table name
	 */
	@Override
	public Table qualifiedTable(String schema, String tableName) {
		return new Table(schema, tableName);
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
	@Override
	public ParmMarker parm(int jdbcType,Object value) {
		return new ParmMarker(jdbcType,value);
	}

	//Expressions
	/**
	 * Create an SQL string expression from a java String.
	 * The string value is enclosed in single quotes (') automatically.
	 * 
	 * @param value the string value
	 * @return a string expression
	 */
	@Override
	public IStringExpression stringExpression(String value) {
		return new StringExpression(value);
	}
	
	/**
	 * Create an SQL string expression using a exprssion value (Column, parameter marker etc)
	 * @param firstExpr the first part of the expression
	 * @return a string expression
	 */
	public IStringExpression stringExpression(ISQLType firstExpr) {
		return new StringExpression(firstExpr);
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a java number value 
	 * (float, int, short etc)
	 * @param value the java number type
	 * @return a mathematical expression
	 */
	@Override
	public IMathExpression mathExpression(Number value) {
		return new MathExpression(constant(value));
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a expression
	 * value (column, parameter marker etc)
	 * @param value an expression type value
	 * @return a mathematical expression
	 */
	@Override
	public IMathExpression mathExpression(ISQLType value) {
		return new MathExpression(value);
	}
	
	//CASE
	/**
	 * Build a CASE WHEN value statement
	 * @return a CASE statement builder
	 */
	@Override
	public ICaseBuilder caseClause() {
		return new CaseClauseBuilder();
	}
	
	/**
	 * Build a CASE value statement
	 * @param caseValue value for the CASE clause
	 * @return a CASE statement builder 
	 */
	@Override
	public ICaseBuilder caseClause(ISQLType caseValue) {
		return new CaseClauseBuilder(caseValue);
	}
	
	/**
	 * Build a CASE math expression statement
	 * @param mathExpr math expression for the CASE clause
	 * @return a CASE statement builder 
	 */
	@Override
	public ICaseBuilder caseClause(IExpressionNode mathExpr) {
		return new CaseClauseBuilder(mathExpr);
	}
	
	/**
	 * Create an ORDER BY number clause entry IE ORDER BY 1 
	 * @param position number argument
	 * @return an <code>OrderByEntry</code>
	 */
	@Override
	public IOrderByEntry orderBy(int position) {

		IOrderByEntry entry = null;
		entry = new OrderByEntry(SQLTypeFactory.getInstance().constant(position));

		return entry;
		
	}
	
	/**
	 * Create an ORDER BY sql type clause entry IE ORDER BY COL01
	 * @param expr SQL column argument
	 * @return an <code>OrderByEntry</code>
	 */
	@Override
	public IOrderByEntry orderBy(ISQLType expr) {
		
		IOrderByEntry entry = null;
		entry = new OrderByEntry(expr);
		return entry;
	}
	
	/**
	 * Create an ORDER BY expression clause entry IE ORDER BY 10 + 10 
	 * @param expr SQL expression argument
	 * @return an <code>OrderByEntry</code>
	 */
	@Override
	public IOrderByEntry orderBy(IExpressionNode expr) {
		
		IOrderByEntry entry = null;
		entry = new OrderByEntry(expr);
		return entry;
	}

}
