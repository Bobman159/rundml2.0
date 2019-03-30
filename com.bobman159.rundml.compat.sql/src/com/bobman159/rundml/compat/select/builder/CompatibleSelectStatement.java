package com.bobman159.rundml.compat.select.builder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.compat.select.steps.CompatibleSelectFetchStep;
import com.bobman159.rundml.compat.select.steps.CompatibleSelectFromStep;
import com.bobman159.rundml.compat.select.steps.CompatibleSelectListStep;
import com.bobman159.rundml.compat.select.steps.CompatibleSelectOrderStep;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;
import com.bobman159.rundml.core.sql.SQLClauses.SQLClause;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.core.util.RunDMLUtils;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

/**
 * Defines a compatbile SQL SELECT statement that can be executed on different DBMS platforms.
 * 
 * <b> No guarantee is made regarding the compatibility of this SELECT builder on all DBMS platforms.
 *
 */
public class CompatibleSelectStatement implements CompatibleSelectListStep, 
												  CompatibleSelectFromStep,
												  CompatibleSelectOrderStep,
												  CompatibleSelectFetchStep {
	
	private Logger logger = LogManager.getLogger(CompatibleSelectStatement.class);
	private Connection conn;
	private DefaultConnectionProvider provider;
	private SQLStatementModel model = new SQLStatementModel();	
	
	/**
	 * Create a compatible SELECT statement to be executed on a database.
	 * @param conn - JDBC connection for a database.
	 */
	public CompatibleSelectStatement(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Create a SELECT statement that may be executed against a database
	 * @param provider - a JDBC connection pool provider for a database
	 */
	public CompatibleSelectStatement(DefaultConnectionProvider provider) {
		this.provider = provider;
	}
	
	/**
	 *  Specify a SELECT * to select all columns in a table for the SELECT statement 
	 */
	@Override
	public CompatibleSelectListStep selectStar() {
		model.addClause(SQLClause.SELECT);
		model.addClause(SQLClause.SELECTSTAR);
		return this;
	}

	/**
	 * Specify the start of a SELECT statement
	 */
	@Override
	public CompatibleSelectListStep select() {
		model.addClause(SQLClause.SELECT);
		return this;
	}

	/**
	 * Specify a SELECT statement using a </code>TableDefinition</code>
	 * Generates a "SELECT column-name[,] FROM schema.tbName clause
	 */
	@Override
	public CompatibleSelectFromStep select(TableDefinition tbDef) {
		model.addClause(SQLClause.SELECT);
		model.addColumnList(SQLClause.SELECTEXPR, tbDef);
		model.addClause(SQLClause.FROM,tbDef.qualifedTableName());
		return this;
	}
	
	/**
	 * Specify the ALL clause for the SELECT statement.
	 */
	@Override
	public CompatibleSelectListStep all() {
		model.addClause(SQLClause.ALL);
		return this;
	}

	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 */
	@Override
	public CompatibleSelectListStep distinct() {
		model.addClause(SQLClause.DISTINCT);
		return this;
	}
	
	/**
	 * Add an expression to be selected for the SELECT statement
	 * @param - an <code>IExpression</code> 
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public CompatibleSelectListStep selectExpression(IExpression expr) {
		model.addExpressionList(SQLClause.SELECTEXPR, expr);
		return this;
	}

	/**
	 * Specify the table for the SELECT statement
	 * @param - schema - the table name schema
	 * @param - tbName - the table name
	 */
	@Override
	public CompatibleSelectFromStep from(String schema, String tbName) {
		model.addClause(SQLClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		return this;
	}
	
	/**
	 * Specify the WHERE clause for the SELECT statement to return table
	 * rows matching the Predicate condition.
	 * 
	 * @param - pred - the predicate conditions
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 */
	@Override
	public CompatibleSelectOrderStep where(Predicate pred) {
		model.addClause(SQLClause.WHERE,pred);
		return this;
	}

	/**
	 * Specify a GROUP BY clause for the SELECT statement.
	 * @param - list of expressions 1 to n 
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public CompatibleSelectOrderStep groupBy(IExpression... groupByExprs) {
		model.addExpressionList(SQLClause.GROUPBY, groupByExprs);
		return this;
	}
	
	/**
	 * Specifies a HAVING clause for the SELECT statement
	 * @param - a <code>Predicate</code>
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 */
	@Override
	public CompatibleSelectStatement having(Predicate havingPred) {
		model.addClause(SQLClause.HAVING,havingPred);
		return this;
	}

	/**
	 * Specify a ORDER BY clause for the SELECT statement.
	 * @param - orderByExprs - expression(s) for the ORDER BY
	 * @see com.bobman159.rundml.core.sql.OrderByExpression
	 */
	@Override
	public CompatibleSelectOrderStep orderBy(OrderByExpression... orderByExprs) {
		model.addExpressionList(SQLClause.ORDERBY, orderByExprs);
		return this;
	}

	/**
	 * Execute the generated SELECT statement and return the results
	 * @return - a <code>List</code> of the resulting rows, empty otherwise.
	 */
	@Override
	public List<String> fetch() {
		return new ArrayList<>();
	}
	
	/**
	 * Returns the SQL SELECT statement text for the current  
	 * statement instance.
	 * @return - the SELECT statement text
	 */
	public String toStmt() {
		return SQLStatementSerializer.serializeSelect(model);
	}

}
