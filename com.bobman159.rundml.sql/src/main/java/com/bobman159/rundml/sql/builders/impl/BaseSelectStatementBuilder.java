package com.bobman159.rundml.sql.builders.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.bobman159.rundml.core.exceptions.RunDMLException;
import com.bobman159.rundml.core.exceptions.RunDMLExceptionListeners;
import com.bobman159.rundml.core.expressions.IExpressionList;
import com.bobman159.rundml.core.model.ICoreModelFactory;
import com.bobman159.rundml.core.model.ISelectModel;
import com.bobman159.rundml.core.model.impl.CoreModelFactory;
import com.bobman159.rundml.core.model.impl.SelectClause;
import com.bobman159.rundml.core.predicates.IPredicatesList;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByList;
import com.bobman159.rundml.core.sql.serialize.impl.SQLStatementSerializer;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.impl.SQLTypeFactory;
import com.bobman159.rundml.core.util.CoreUtils;
import com.bobman159.rundml.jdbc.select.execution.RunDMLExecutor;
import com.bobman159.rundml.sql.builders.ISelectBuilder;

/**
 * Defines a basic SQL SELECT statement that can be executed on different DBMS
 * platforms. This class can be sub classed to provide a default implementation
 * of the <code>IStatementBuilder
 * </code> and <code>ISelectBuilder</code> interfaces.
 * 
 * <ul>
 * <li><b>No syntax validation or type validation is done by the
 * builder</b></li>
 * <li><b> No guarantee is made regarding the compatibility of this SELECT
 * builder on all DBMS platforms.</b></li>
 * </ul>
 */
@SuppressWarnings("rawtypes")
public class BaseSelectStatementBuilder <B extends BaseSelectStatementBuilder> implements ISelectBuilder {

	
	private ISelectModel model = CoreModelFactory.getInstance().createSelectModel();

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
		try {
			ISQLType[] columnArray = CoreUtils.createColumnsFromClass(clazz);
			model.addSelectExpression(columnArray);
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
	 * expression i.e. "COL01" + 10 or "COL02 || 'def' the select(ISQLType...
	 * expr) method should be used with the <code>IExpressionFactory</code> method should be
	 * used.
	 * 
	 * @param columns one or more column names as string values
	 * @return an instance of this builder
	 */
	public B select(String... columns) {

		model.addSelectExpression(CoreUtils.createColumnsFromStrings(columns));

		return self();
	}

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
	public B select(ISQLType... exprs) {

		model.addSelectExpression(exprs);

		return self();
	}

	/**
	 * Add a single SELECT expression 
	 * @param expr the expression to be add
	 * @return an instance of this builder
	 */
	public B selectExpression(ISQLType expr) {
		model.addSelectExpression(expr);
		return self();
	}

	/**
	 * Specify the ALL clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public B all() {
		model.putProp(SelectClause.ALL, Boolean.valueOf(true));
		return self();
	}

	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 * 
	 * @return an instance of this builder
	 */
	public B distinct() {
		model.putProp(SelectClause.DISTINCT, Boolean.valueOf(true));
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
		model.putProp(SelectClause.FROM, SQLTypeFactory.getInstance().qualifiedTable(schema, tbName));
		return self();
	}

	/**
	 * Specify the WHERE clause for the SELECT statement to return table rows
	 * matching the predicate condtion.
	 * @param predList predicate
	 * @return
	 */
	public B where(IPredicatesList predList) {
		model.putProp(SelectClause.WHERE, predList);
		return self();
	}

	/**
	 * Specify a GROUP BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.types.IExpression
	 * @param groupByExprs list of expressions 1 to n
	 * @return an instance of this builder
	 */
	public B groupBy(ISQLType... groupByExprs) {
		IExpressionList groupByExprList = CoreModelFactory.getInstance().createExpressionList();
		groupByExprList.addExpressions(groupByExprs);
		model.putProp(SelectClause.GROUPBY, groupByExprs);
		return self();
	}

	/**
	 * Specifies a HAVING clause for the SELECT statement
	 * 
	 * @see com.bobman159.rundml.core.predicates.impl.PredicateBuilder
	 * @param havingPred a <code>PredicateBuilder</code>
	 * @return an instance of this builder
	 */
	public B having(IPredicatesList havingPred) {
		model.putProp(SelectClause.HAVING, havingPred);
		return self();
	}

	/**
	 * Specify a ORDER BY clause for the SELECT statement.
	 * 
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry
	 * @param orderByExprs expression(s) for the ORDER BY
	 * @return an instance of this builder
	 */
	public B orderBy(IOrderByEntry... orderByExprs) {
	
		IOrderByList orderByList = CoreModelFactory.getInstance().createOrderByList();
		orderByList.addOrderByClause(orderByExprs);
		model.putProp(SelectClause.ORDERBY, orderByList);
		return self();
	}
	
	/**
	 * @see com.bobman159.rundml.sql.builders.impl.IStatementBuilder.ISQLStatement#toSQL()
	 */
	@Override
	public String toSQL() {
		return SQLStatementSerializer.serializeSelect(this);
	}

	/**
	 * @see com.bobman159.rundml.sql.builders.impl.ISelectBuilder.ISQLSelect#execute(Connection, Class)
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
	
	public ISelectBuilder build() {
		return model;
	}

	@SuppressWarnings("unchecked")
	private final B self() {
		return (B) this;
	}

}