package com.bobman159.rundml.h2.select.builder;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.model.SQLStatementSerializer;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.sql.OrderByExpression;
import com.bobman159.rundml.core.sql.SQLClauses.SQLClause;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.core.util.RunDMLUtils;
import com.bobman159.rundml.h2.select.builder.H2SQLClauses.H2SelectClause;
import com.bobman159.rundml.h2.select.steps.H2SelectFetchStep;
import com.bobman159.rundml.h2.select.steps.H2SelectFromStep;
import com.bobman159.rundml.h2.select.steps.H2SelectListStep;
import com.bobman159.rundml.h2.select.steps.H2SelectOrderStep;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;

/**
 * Define a SELECT statement that can be executed against a table in an 
 * H2 database.
 *
 */
public class H2SelectStatement implements  H2SelectListStep,
										   H2SelectFromStep,
										   H2SelectOrderStep,
										   H2SelectFetchStep {

	private Logger logger = LogManager.getLogger(H2SelectStatement.class);
	private SQLStatementModel model = new SQLStatementModel();
	private Connection conn = null;
	private DefaultConnectionProvider provider = null;
	
	/**
	 * Create a SELECT statement that may be executed against an H2 database
	 * 
	 * @param conn - JDBC connection for the H2 database
	 */
	public H2SelectStatement(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * Create a SELECT statement that may be executed against an H2 database
	 * 
	 * @param provider - a JDBC connection pool provider for an H2 database.
	 */
	
	public H2SelectStatement(DefaultConnectionProvider provider) {
		this.provider = provider;
	}
	

	/**
	 * Specify a SELECT * to select all columns in a table 
	 * for the SELECT statement.
	 */
	@Override
	public H2SelectListStep selectStar() {
 		model.addClause(SQLClause.SELECT);
		model.addClause(SQLClause.SELECTSTAR);
		return this;
	}
	
	/** Specify the start of a SELECT statement
	 * 
	 */
	@Override
	public H2SelectListStep select() {
		model.addClause(SQLClause.SELECT);
		return this;
	}
	
	/**
	 * Specify a SELECT statement using a <code>TableDefinition</code>
	 * Generates a "SELECT column-name [,] FROM schema.tbName clause
	 */
	@Override
	public H2SelectFromStep select(TableDefinition tbDef) {
		
		model.addClause(SQLClause.SELECT);
		model.addColumnList(SQLClause.SELECTEXPR, tbDef);
		model.addClause(SQLClause.FROM,tbDef.qualifedTableName());
		
		return this;
	}
	
	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 */
	@Override
	public H2SelectListStep distinct() {
		model.addClause(SQLClause.DISTINCT);
		return this;
	}
	
	/**
	 * Specify the ALL clause for the SELECT statement
	 */
	@Override
	public H2SelectListStep all() {
		model.addClause(SQLClause.ALL);
		return this;
	}
	
	/**
	 * Add an expression to be selected for the SELECT statement
	 * @param - an <code>IExpression</code> 
	 * 
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectListStep selectExpression(IExpression expr) {
		
		model.addExpressionList(SQLClause.SELECTEXPR, expr);
		return this;
	}
	
	/**
	 * Specify the table for the SELECT statement
	 * 
	 * @param schema - the table name schema
	 * @param tBName - the table name
	 */
	@Override
	public H2SelectStatement from(String schema, String tbName) {
		model.addClause(SQLClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		return this;
	}
	
	/**
	 * Specify the WHERE clause for the SELECT statement to 
	 * return table rows matching the Predicate condition.
	 * 
	 * @param - the SQL predicate
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 */
	@Override
	public H2SelectStatement where(Predicate pred) {
		model.addClause(SQLClause.WHERE,pred);
		return this;
	}
	
	/**
	 * Specify a ORDER BY clause for the SELECT statement.
	 * @param orderByExprs - expression(s) for the ORDER BY
	 * @see com.bobman159.rundml.core.sql.OrderByExpression
	 */
	@Override
	public H2SelectStatement orderBy(OrderByExpression... orderByExprs) {
		model.addExpressionList(SQLClause.ORDERBY, orderByExprs);
		return this;
	}
	
	/**
	 * 
	 * Specify a GROUP BY clause for the SELECT statement.
	 * @param - list of expressions 1 to n
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectStatement groupBy(IExpression... groupByExprs) {
		model.addExpressionList(SQLClause.GROUPBY, groupByExprs);
		return this;
	}
		
	/**
	 * Specify a HAVING clause for the SELECT statement.
	 * @param - a <code>Predicate</code>
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 */
	@Override
	public H2SelectStatement having(Predicate havingPred) {
		model.addClause(SQLClause.HAVING,havingPred);
		return this;
	}
	
	/**
	 * Specify a LIMIT clause for the SELECT statement
	 * @param - an expression 
	 * @see com.rob159.rundml.core.exprtypes.IExpression	 * 
	 * 
	 */
	@Override
	public H2SelectStatement limit(IExpression limitTerm) {
		model.addClause(H2SelectClause.LIMIT,limitTerm);
		return this;
	}
	
	/**
	 * Specify an OFFSET clause for the LIMIT clause in a SELECT
	 * statement.
	 * @param - offset 
	 */
	@Override
	public H2SelectOrderStep offset(IExpression offset) {
		model.addClause(H2SelectClause.OFFSET,offset);
		return this;
	}

	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param - an expression 
	 * @see com.rob159.rundml.core.exprtypes.IExpression	 * 
	 * 
	 */
	@Override
	public H2SelectListStep top(IExpression topExpr) {
		model.addClause(H2SelectClause.TOP,topExpr);
		return this;
	}

	/**
	 * Execute the generated SELECT statement and return the results 
	 * @return - a <code>List</code> of the resulting rows, empty otherwise
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
