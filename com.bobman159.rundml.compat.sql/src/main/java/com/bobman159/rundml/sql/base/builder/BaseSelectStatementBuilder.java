package com.bobman159.rundml.sql.base.builder;

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
import com.bobman159.rundml.jdbc.select.ITableRow;
import com.bobman159.rundml.sql.ISQLSelect;
import com.bobman159.rundml.sql.ISQLStatement;

/**
 * Defines a basic SQL SELECT statement that can be executed on different DBMS platforms.
 * This class can be sub classed to provide a default implementation of the <code>ISQLStatement
 * </code> and <code>ISQLSelect</code> interfaces.
 * 
 * <ul>
 * <li><b>No syntax validation or type validation is done by the builder</b></li>
 * <li><b> No guarantee is made regarding the compatibility of this SELECT builder on all 
 * 			DBMS platforms.</b></li>
 * </ul>
 */
@SuppressWarnings("rawtypes")
public class BaseSelectStatementBuilder<B extends BaseSelectStatementBuilder> implements ISQLStatement, 
																						 ISQLSelect {
	
	private Logger logger = LogManager.getLogger(BaseSelectStatementBuilder.class);
	private TableDefinition tbDef;
	protected SQLStatementModel model = new SQLStatementModel();	
	
	/**
	 * Create a compatible SELECT statement to be executed on a database.
	 * @param mapper table mapping definition for the SELECT
	 */
	public BaseSelectStatementBuilder(TableDefinition mapper) {
		this.tbDef = mapper;
	}

	/**
	 *  Specify a SELECT * to select all columns in a table for the SELECT statement 
	 *  @return an instance of this builder
	 */
	public B selectStar() {
		model.addClause(SQLClause.SELECT);
		model.addClause(SQLClause.SELECTSTAR);
		return self();
	}

	/**
	 * Specify the start of a SELECT statement
	 * @return an instance of this builder
	 */
	public B select() {
		model.addClause(SQLClause.SELECT);
		return self();
	}

	/**
	 * Specify a SELECT statement using a <code>TableDefinition</code>
	 * Generates a "SELECT column-name[,] FROM schema.tbName clause
	 * @see com.bobman159.rundml.core.tabledef.TableDefinition
	 * @param tbDef an instance of a <code>TableDefinition</code>
	 * @return an instance of this builder
	 */
	public B select(TableDefinition tbDef) {
		model.addClause(SQLClause.SELECT);
		model.addColumnList(SQLClause.SELECTEXPR, tbDef);
		model.addClause(SQLClause.FROM,tbDef.qualifedTableName());
		return self();
	}
	
	/**
	 * Specify the ALL clause for the SELECT statement.
	 * @return an instance of this builder
	 */
	public B all() {
		model.addClause(SQLClause.ALL);
		return self();
	}

	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 * @return an instance of this builder
	 */
	public B distinct() {
		model.addClause(SQLClause.DISTINCT);
		return self();
	}
	
	/**
	 * Add an expression to be selected for the SELECT statement
	 * @see com.bobman159.rundml.core.exprtypes.IExpression
	 * @param expr an <code>IExpression</code> to be selected
	 * @return an instance of this builder
	 */
	public B selectExpression(IExpression expr) {
		model.addExpressionList(SQLClause.SELECTEXPR, expr);
		return self();
	}

	/**
	 * Specify the table for the SELECT statement
	 * @param schema the table name schema
	 * @param tbName the table name
	 * @return an instance of this builder
	 */
	public B from(String schema, String tbName) {
		model.addClause(SQLClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		return self();
	}
	
	/**
	 * Specify the WHERE clause for the SELECT statement to return table
	 * rows matching the Predicate condition.
	 * 
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 * @param pred the predicate conditions
	 * @return an instance of this builder
	 */
	public B where(Predicate pred) {
		model.addClause(SQLClause.WHERE,pred);
		return self();
	}

	/**
	 * Specify a GROUP BY clause for the SELECT statement.
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
	 * @see com.bobman159.rundml.core.predicates.Predicate
	 * @param havingPred a <code>Predicate</code>
	 * @return an instance of this builder
	 */
	public B having(Predicate havingPred) {
		model.addClause(SQLClause.HAVING,havingPred);
		return self();
	}

	/**
	 * Specify a ORDER BY clause for the SELECT statement.
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
	 * @see com.bobman159.rundml.sql.ISQLSelect#execute(Connection)
	 */
	@Override
	public List<ITableRow> execute(Connection conn) {
		List<ITableRow> results = new ArrayList<>();
		Future <List<ITableRow>> task;
		
		/*
		 * For now this seems to work well in that it executes the SELECT
		 * in another thread. Since I don't expect to be using this for 
		 * results > 5000 rows.  I will leave it this way for now.
		 */
		task = RunDMLExecutor.getInstance().executeSelect(conn, model,tbDef);			
		
		try {
			results = task.get();
		} catch (CancellationException | ExecutionException | InterruptedException ex) {
			logger.error(ex.getMessage(), ex);
			Thread.currentThread().interrupt();
		}
			
		return results;

	}
	
	@SuppressWarnings("unchecked")
	private final B self() {
		return (B) this;
	}

}