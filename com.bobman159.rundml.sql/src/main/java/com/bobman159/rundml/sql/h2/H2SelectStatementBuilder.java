package com.bobman159.rundml.sql.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
import com.bobman159.rundml.jdbc.execution.RunDMLExecutor;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.jdbc.select.ITableRow;
import com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.h2.H2SQLClauses.H2SelectClause;

/**
 * Define a SELECT statement that can be executed against a table in an 
 * H2 database.
 *
 */
public class H2SelectStatementBuilder extends BaseSelectStatementBuilder<H2SelectStatementBuilder> {

	private Logger logger = LogManager.getLogger(H2SelectStatementBuilder.class);
	private TableDefinition tbDef = null;
	
	/**
	 * Create a SELECT statement that may be executed against an H2 database
	 * 
	 * @param mapper table mapping definition for the SELECT
	 */
	public H2SelectStatementBuilder(TableDefinition mapper) {
		super(mapper);
		this.tbDef = mapper;
	}

//	/**
//	 * Specify a SELECT * to select all columns in a table 
//	 * for the SELECT statement.
//	 */
//	@Override
//	public H2SelectStatementBuilder selectStar() {
// 		model.addClause(SQLClause.SELECT);
//		model.addClause(SQLClause.SELECTSTAR);
//		return this;
//	}
	
//	/** Specify the start of a SELECT statement
//	 * 
//	 */
//	@Override
//	public H2SelectListStep select() {
//		model.addClause(SQLClause.SELECT);
//		return this;
//	}
	
//	/**
//	 * Specify a SELECT statement using a <code>TableDefinition</code>
//	 * Generates a "SELECT column-name [,] FROM schema.tbName clause
//	 */
//	@Override
//	public H2SelectFromStep select(TableDefinition tbDef) {
//		
//		model.addClause(SQLClause.SELECT);
//		model.addColumnList(SQLClause.SELECTEXPR, tbDef);
//		model.addClause(SQLClause.FROM,tbDef.qualifedTableName());
//		
//		return this;
//	}
	
//	/**
//	 * Specify the DISTINCT clause for the SELECT statement.
//	 */
//	@Override
//	public H2SelectListStep distinct() {
//		model.addClause(SQLClause.DISTINCT);
//		return this;
//	}
	
//	/**
//	 * Specify the ALL clause for the SELECT statement
//	 */
//	@Override
//	public H2SelectListStep all() {
//		model.addClause(SQLClause.ALL);
//		return this;
//	}
	
//	/**
//	 * Add an expression to be selected for the SELECT statement
//	 * @param expr an expression for the SELECT clause
//	 * 
//	 * @see com.bobman159.rundml.core.exprtypes.IExpression
//	 */
//	@Override
//	public H2SelectListStep selectExpression(IExpression expr) {
//		
//		model.addExpressionList(SQLClause.SELECTEXPR, expr);
//		return this;
//	}
	
//	/**
//	 * Specify the table for the SELECT statement
//	 * 
//	 * @param schema the schema of the table 
//	 * @param tbName the name of the table 
//	 * @return the SELECT statement builder
//	 */
//	@Override
//	public H2SelectStatementBuilder from(String schema, String tbName) {
//		model.addClause(SQLClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
//		return this;
//	}
	
//	/**
//	 * Specify the WHERE clause for the SELECT statement to 
//	 * return table rows matching the Predicate condition.
//	 * 
//	 * @param pred the SQL predicate
//	 * @return the SELECT statement builder
//	 * @see com.bobman159.rundml.core.predicates.Predicate
//	 */
//	@Override
//	public H2SelectStatementBuilder where(Predicate pred) {
//		model.addClause(SQLClause.WHERE,pred);
//		return this;
//	}
	
//	/**
//	 * Specify a ORDER BY clause for the SELECT statement.
//	 * @param orderByExprs expression(s) for the ORDER BY
//	 * @return the SELECT statement builder
//	 * @see com.bobman159.rundml.core.sql.OrderByExpression
//	 */
//	@Override
//	public H2SelectStatementBuilder orderBy(OrderByExpression... orderByExprs) {
//		model.addExpressionList(SQLClause.ORDERBY, orderByExprs);
//		return this;
//	}
	
//	/**
//	 * 
//	 * Specify a GROUP BY clause for the SELECT statement.
//	 * @param groupByExprs of expressions 1 to n
//	 * @return the SELECT statement builder
//	 * @see com.bobman159.rundml.core.exprtypes.IExpression
//	 */
//	@Override
//	public H2SelectStatementBuilder groupBy(IExpression... groupByExprs) {
//		model.addExpressionList(SQLClause.GROUPBY, groupByExprs);
//		return this;
//	}
		
//	/**
//	 * Specify a HAVING clause for the SELECT statement.
//	 * @param havingPred a predicate clause for the HAVING
//	 * @return the SELECT statement builder
//	 * @see com.bobman159.rundml.core.predicates.Predicate
//	 */
//	@Override
//	public H2SelectStatementBuilder having(Predicate havingPred) {
//		model.addClause(SQLClause.HAVING,havingPred);
//		return this;
//	}
	
	/**
	 * Specify a LIMIT clause for the SELECT statement
	 * @param limitTerm an expression to limit the number of results returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * 
	 */
//	@Override
	public H2SelectStatementBuilder limit(IExpression limitTerm) {
		model.addClause(H2SelectClause.LIMIT,limitTerm);
		return this;
	}
	
	/**
	 * Specify an OFFSET clause for the LIMIT clause in a SELECT
	 * statement.
	 * @param offset specifies how many rows to skip
	 * @return the SELECT statement builder
	 */
//	@Override
	public H2SelectStatementBuilder offset(IExpression offset) {
		model.addClause(H2SelectClause.OFFSET,offset);
		return this;
	}

	/**
	 * Specify a TOP clause for the SELECT statement
	 * @param topExpr an expression that limits the number of rows returned
	 * @return the SELECT statement builder
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * 
	 */
//	@Override
	public H2SelectStatementBuilder top(IExpression topExpr) {
		model.addClause(H2SelectClause.TOP,topExpr);
		return this;
	}

//	/**
//	 * Execute the generated SELECT statement and return the results 
//	 * @return a <code>List</code> of the resulting rows, empty if an error occurred
//	 */
//	@Override
//	public List<ITableRow> fetch() {
//		
//		List<ITableRow> results = new ArrayList<>();
//		Future <List<ITableRow>> task;
//		
//		/*
//		 * For now this seems to work well in that it executes the SELECT
//		 * in another thread. Since I don't expect to be using this for 
//		 * results > 5000 rows.  I will leave it this way for now.
//		 */
//		if (conn != null) {
//			task = RunDMLExecutor.getInstance().executeSelect(conn, model,tbDef);			
//		} else {
//			//ASSUME: provider != null if the connection is null
//			task = RunDMLExecutor.getInstance().executeSelect(
//						provider.getConnection(), model,tbDef);
//		}
//		
//		try {
//			results = task.get();
//		} catch (CancellationException | ExecutionException | InterruptedException ex) {
//			logger.error(ex.getMessage(), ex);
//			Thread.currentThread().interrupt();
//		}
//			
//		return results;
//	}
	
//	/**
//	 * Returns the SQL SELECT statement text for the current  
//	 * statement instance.
//	 * @return the SELECT statement text
//	 */
//	public String toStmt() {
//		return SQLStatementSerializer.serializeSelect(model);
//	}

}
