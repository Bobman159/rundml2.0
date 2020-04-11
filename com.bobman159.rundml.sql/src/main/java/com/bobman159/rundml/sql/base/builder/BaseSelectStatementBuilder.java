package com.bobman159.rundml.sql.base.builder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;
import com.bobman159.rundml.core.sql.SQLClauses.SQLClause;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.jdbc.select.execution.RunDMLExecutor;
import com.bobman159.rundml.sql.ISQLSelect;
import com.bobman159.rundml.sql.ISQLStatement;

/**
 * Defines a basic SQL SELECT statement that can be executed on different DBMS
 * platforms. This class can be sub classed to provide a default implementation
 * of the <code>ISQLStatement
 * </code> and <code>ISQLSelect</code> interfaces.
 * 
 * <ul>
 * <li><b>No syntax validation or type validation is done by the
 * builder</b></li>
 * <li><b> No guarantee is made regarding the compatibility of this SELECT
 * builder on all DBMS platforms.</b></li>
 * </ul>
 */
@SuppressWarnings("rawtypes")
public class BaseSelectStatementBuilder<B extends BaseSelectStatementBuilder> implements ISQLStatement, ISQLSelect {

	private Logger logger = LogManager.getLogger(BaseSelectStatementBuilder.class);
	protected SQLStatementModel model = new SQLStatementModel();

	public BaseSelectStatementBuilder() {
		CoreUtils.initRunDML();
	}

	/**
	 * Specify an SQL SELECT statement start. This method should be used when
	 * creating a "SELECT ALL" or "SELECT DISTINCT" SQL statement.
	 * 
	 * @return an instance of this builder
	 */
	public B select() {

		model.addClause(SQLClause.SELECT);
		return self();

	}

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
	public B select(Class<?> clazz) {
		model.addClause(SQLClause.SELECT);
		try {
			IExpression[] columnArray = CoreUtils.createColumnsFromClass(clazz);
			model.addExpressionList(SQLClause.SELECTEXPR, columnArray);
		} catch (RunDMLException rdex) {
			RunDMLExceptionListeners.getInstance().notifyListeners(rdex);
		}

		return self();
	}

	/**
	 * A convenience method to specify the start of an SQL SELECT statement where a
	 * list of String values "ABC","DEF" is treated as the list of column names in
	 * the SELECT list. For example select("Col01","Col02") will generate "SELECT
	 * COL01,COL02" as an SQL statement. If a column name should be used in an SQL
	 * expression i.e. "COL01" + 10 or "COL02 || 'def' the select(IExpression...
	 * expr) method should be used with the <code>Expression</code> method should be
	 * used.
	 * 
	 * @param columns one or more column names as string values
	 * @return an instance of this builder
	 */
	public B select(String... columns) {

		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, CoreUtils.createColumnsFromStrings(columns));

		return self();
	}

	/**
	 * Specify the start of a SELECT statement that will use SQL expressions in the
	 * select list. SQL expressions like "NUMBCOL + 10" and "CHARCOL || 'string'"
	 * should use this method to build those expressions.
	 * 
	 * <pre>
	 * {code
	 * 	List &lt;Object&gt; rows2 = RunDMLSQLFactory.createBaseSelectStatement()
	 * 			.select(Expression.number(10), Expression.string("This is a string"),
	 * 					Expression.column("DFLTINTEGER"), Expression.parm(Types.VARCHAR, "This is a string too"),
	 * 					Expression.column("DFLTINTEGER").add(10),
	 * 					Expression.number(10).subtract(Expression.number(5)))
	 * 			.from("rundml", "tableName");
	 * }
	 * </pre>
	 * 
	 * @param exprs one or more <code>IExpression</code> to be selected
	 * @return an instance of this builder
	 */
	public B select(IExpression... exprs) {

		model.addClause(SQLClause.SELECT);
		model.addExpressionList(SQLClause.SELECTEXPR, exprs);

		return self();
	}

	public B selectExpression(IExpression expr) {
		model.addExpressionList(SQLClause.SELECTEXPR, expr);
		return self();
	}

	/**
	 * Specify the ALL clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public B all() {
		model.addClause(SQLClause.ALL);
		return self();
	}

	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public B distinct() {
		model.addClause(SQLClause.DISTINCT);
		return self();
	}

	/**
	 * Specify the table for the SELECT statement
	 * 
	 * @param schema the table name schema
	 * @param tbName the table name
	 * @return an instance of this builder
	 */
	public B from(String schema, String tbName) {
		model.addClause(SQLClause.FROM, CoreUtils.qualifiedTbName(schema, tbName));
		return self();
	}

	/**
	 * Specify the WHERE clause for the SELECT statement to return table rows
	 * matching the Predicate condition.
	 * 
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 * @param pred the predicate conditions
	 * @return an instance of this builder
	 */
	public B where(Predicate pred) {
		model.addClause(SQLClause.WHERE, pred);
		return self();
	}

	/**
	 * Specify a GROUP BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * @param groupByExprs list of expressions 1 to n
	 * @return an instance of this builder
	 */
	public B groupBy(IExpression... groupByExprs) {
		model.addExpressionList(SQLClause.GROUPBY, groupByExprs);
		return self();
	}

	/**
	 * Specifies a HAVING clause for the SELECT statement
	 * 
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 * @param havingPred a <code>Predicate</code>
	 * @return an instance of this builder
	 */
	public B having(Predicate havingPred) {
		model.addClause(SQLClause.HAVING, havingPred);
		return self();
	}

	/**
	 * Specify a ORDER BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.sql.OrderByExpression
	 * @param orderByExprs expression(s) for the ORDER BY
	 * @return an instance of this builder
	 */
	public B orderBy(OrderByExpression... orderByExprs) {
		model.addExpressionList(SQLClause.ORDERBY, orderByExprs);
		return self();
	}

	/**
	 * @see com.bobman159.rundml.sql.ISQLStatement#getStatementText()
	 */
	@Override
	public String getStatementText() {
		return SQLStatementSerializer.serializeSelect(model);
	}

	/**
	 * @see com.bobman159.rundml.sql.ISQLSelect#execute(Connection, Class)
	 */
	@Override
	public List<Object> execute(Connection conn, Class clazz) {

		List<Object> results = new ArrayList<>();

		/*
		 * For now this seems to work well in that it executes the SELECT in another
		 * thread. Since I don't expect to be using this for results > 5000 rows. I will
		 * leave it this way for now.
		 */
		results = RunDMLExecutor.getInstance().executeSelect(conn, model, clazz);

		return results;

	}

	@SuppressWarnings("unchecked")
	private final B self() {
		return (B) this;
	}

}