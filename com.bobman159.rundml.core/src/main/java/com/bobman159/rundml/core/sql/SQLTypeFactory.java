package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.expressions.IExpressionNode;
import com.bobman159.rundml.core.expressions.IMathExpression;
import com.bobman159.rundml.core.expressions.IStringExpression;
import com.bobman159.rundml.core.expressions.impl.MathExpression;
import com.bobman159.rundml.core.expressions.impl.StringExpression;
import com.bobman159.rundml.core.sql.impl.CaseClauseBuilder;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.Column;
import com.bobman159.rundml.core.sql.types.impl.NumericType;
import com.bobman159.rundml.core.sql.types.impl.ParmMarker;
import com.bobman159.rundml.core.sql.types.impl.StringType;
import com.bobman159.rundml.core.sql.types.impl.Table;

/**
 * Factory class for the creation of SQL types 
 *
 */
public class SQLTypeFactory { 
	
	private static SQLTypeFactory self = null;
	
	/**
	 * @return an instance of the SQLTypeFactory
	 */
	public static SQLTypeFactory getInstance() {
		if (self == null) {
			self = new SQLTypeFactory();
		}
		return self;
	}
	
	protected SQLTypeFactory() {
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
	public NumericType constant(Number numb) {
		return new NumericType(numb);
	}

	/**
	 * Create a string value expression type from a java primitive type 
	 * @param value the string value
	 * @return a <code>StringType</code> expression value
	 */
	public ISQLType constant(String value) {
		return new StringType(value);
	}
	
	/**
	 * Crate unqualified database table value type 
	 * @param tableName the database table name 
	 * @return a <code>Table</code> type
	 */
	public Table unQualifiedTable(String tableName) {
		return new Table(tableName);
	}
	
	/**
	 * Create a qualified (schema.tableName) database table value type
	 * @param schema the database schema for the table
	 * @param tableName the database table name
	 */
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
	public IMathExpression mathExpression(Number value) {
		return new MathExpression(constant(value));
	}
	
	/**
	 * Create an SQL math expression (add, subtract, multiply, divide) using a expression
	 * value (column, parameter marker etc)
	 * @param value an expression type value
	 * @return a mathematical expression
	 */
	public IMathExpression mathExpression(ISQLType value) {
		return new MathExpression(value);
	}
	
	//CASE
	/**
	 * Build a CASE WHEN value statement
	 * @return a CASE statement builder
	 */
	public ICaseBuilder caseClause() {
		return new CaseClauseBuilder();
	}
	
	/**
	 * Build a CASE value statement
	 * @param caseValue value for the CASE clause
	 * @return a CASE statement builder 
	 */
	public ICaseBuilder caseClause(ISQLType caseValue) {
		return new CaseClauseBuilder(caseValue);
	}
	
	/**
	 * Build a CASE math expression statement
	 * @param mathExpr math expression for the CASE clause
	 * @return a CASE statement builder 
	 */
	public ICaseBuilder caseClause(IExpressionNode mathExpr) {
		return new CaseClauseBuilder(mathExpr);
	}

}
