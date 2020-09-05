package com.bobman159.rundml.sql.builders;

import java.sql.Connection;
import java.util.List;

import com.bobman159.rundml.core.model.ISelectStatement;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Define the basic implementation for an SQL SELECT statement to use when using RunDML.
 *
 */
public interface ISelectBuilder extends IStatementBuilder {
	

	/**
	 * Specify an SQL SELECT statement start. This method should be used when
	 * creating a "SELECT ALL" or "SELECT DISTINCT" SQL statement.
	 * 
	 * @return an instance of this builder
	 */
	public ISelectBuilder select();

	/**
	 * Specify an SQL SELECT statement and use the fields in the a table row class
	 * as the column names in the SQL statement (this is the default) expected
	 * behavior. If the class name specified implements the IFieldMap interface, the
	 * field map is used to map any non default field names and the column name in
	 * the IFieldMap is used in the SELECT.
	 * 
	 * @param clazz class name to use for the select list
	 * @return an instance of this builder
	 */
	public ISelectBuilder select(Class<?> clazz);

	/**
	 * A convenience method to specify the start of an SQL SELECT statement where a
	 * list of String values "ABC","DEF" is treated as the list of column names in
	 * the SELECT list. For example select("Col01","Col02") will generate "SELECT
	 * COL01,COL02" as an SQL statement. If a column name should be used in an SQL
	 * expression i.e. "COL01" + 10 or "COL02 || 'def' the select(ISQLType...
	 * expr) method should be used with the <code>IExpressionFactory</code> method should be
	 * used.
	 * 
	 * @param columns one or more column names as string values
	 * @return an instance of this builder
	 */
	public ISelectBuilder select(String... columns);
	
	/**
	 * Specify the start of a SELECT statement that will use SQL expressions in the
	 * select list. SQL expressions like "NUMBCOL + 10" and "CHARCOL || 'string'"
	 * should use this method to build those expressions.
	 * 
	 * <pre>
	 * {code
	 * 	List &lt;Object&gt; rows2 = SQLStatementBuilderFactory.createBaseSelectStatement(conn)
	 * 			.select(IExpressionFactory.number(10), IExpressionFactory.string("This is a string"),
	 * 					IExpressionFactory.column("DFLTINTEGER"), IExpressionFactory.parm(Types.VARCHAR, "This is a string too"),
	 * 					IExpressionFactory.column("DFLTINTEGER").add(10),
	 * 					IExpressionFactory.number(10).subtract(IExpressionFactory.number(5)))
	 * 			.from("rundml", "tableName");
	 * }
	 * </pre>
	 * 
	 * @param exprs one or more <code>ISQLType</code> to be selected
	 * @return an instance of this builder
	 */
	public ISelectBuilder select(ISQLType... exprs);

	/**
	 * Add a single SELECT expression 
	 * @param expr the expression to be add
	 * @return an instance of this builder
	 */
	public ISelectBuilder selectExpression(ISQLType expr);

	/**
	 * Specify the ALL clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public ISelectBuilder all(); 

	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public ISelectBuilder distinct();

	/**
	 * Specify the table for the SELECT statement
	 * 
	 * @param schema the table name schema
	 * @param tbName the table name
	 * @return an instance of this builder
	 */
	public ISelectBuilder from(String schema, String tbName);

	/**
	 * Specify the WHERE clause for the SELECT statement to return table rows
	 * matching the predicate condtion.
	 * @param predList predicate
	 * @return
	 */
	public ISelectBuilder where(IPredicatesList predList);

	/**
	 * Specify a GROUP BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.types.IExpression
	 * @param groupByExprs list of expressions 1 to n
	 * @return an instance of this builder
	 */
	public ISelectBuilder groupBy(ISQLType... groupByExprs);

	/**
	 * Specifies a HAVING clause for the SELECT statement
	 * 
	 * @see com.bobman159.rundml.core.predicates.impl.PredicateBuilder
	 * @param havingPred a <code>PredicateBuilder</code>
	 * @return an instance of this builder
	 */
	public ISelectBuilder having(IPredicatesList havingPred);

	/**
	 * Specify a ORDER BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry
	 * @param orderByExprs expression(s) for the ORDER BY
	 * @return an instance of this builder
	 */
	public ISelectBuilder orderBy(IOrderByEntry... orderByExprs);
	
	/**
	 * 
	 * @return the SQL SELECT that was built
	 */
	public ISelectStatement build();

	
	/**
	 * Execute an SQL SELECT using a database connection and return a list of rows.
	 * @param conn a valid jdbc connection to a database 
	 * @param clazz class object to contain 1 row from the SELECT results
	 * @return a list of rows, if any returned by the SELECT
	 */
	public List<Object> execute(Connection conn,Class<?> clazz);

}
