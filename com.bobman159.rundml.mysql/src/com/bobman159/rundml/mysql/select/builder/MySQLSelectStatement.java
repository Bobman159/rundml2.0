/**
 * 
 */
package com.bobman159.rundml.mysql.select.builder;

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
import com.bobman159.rundml.mysql.select.builder.MySQLClauses.MySQLClause;
import com.bobman159.rundml.mysql.select.steps.MySQLSelectFetchStep;
import com.bobman159.rundml.mysql.select.steps.MySQLSelectFromStep;
import com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep;
import com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep;

/**
 * Define a SELECT statement that can be executed against a table in a MySQL database  
 * 
 */
public class MySQLSelectStatement implements MySQLSelectListStep,
											 MySQLSelectFromStep,
											 MySQLSelectOrderStep,
											 MySQLSelectFetchStep {
	
	private Logger logger = LogManager.getLogger(MySQLSelectStatement.class);
	private SQLStatementModel model;
	private Connection conn = null;
	private DefaultConnectionProvider provider = null;
	private TableDefinition tbDef = null;

	/**
	 * Create a SELECT statement that may be executed against a MySQL database table
	 * @param conn JDBC connection for the MySQL database.
	 * @param mapper table mapping definition for the SELECT
	 */
	public MySQLSelectStatement(Connection conn,TableDefinition mapper) {
		this.conn = conn;
		this.tbDef = mapper;
		model = new SQLStatementModel();
	}
	
	/**
	 * Create a SELECT statement that may be executed against an MySQL database table.
	 * @param provider a JDBC connection pool for an MySQL database
	 * @param mapper table mapping definition for the SELECT 
	 */
	public MySQLSelectStatement(DefaultConnectionProvider provider,TableDefinition mapper) {
		this.provider = provider;
		this.tbDef = mapper;
		model = new SQLStatementModel();
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep#having(Predicate)
	 */
	@Override
	public MySQLSelectOrderStep having(Predicate havingPred) {
		model.addClause(SQLClause.HAVING,havingPred);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep#offset(IExpression)
	 */
	@Override
	public MySQLSelectOrderStep offset(IExpression offSet) {
		model.addClause(MySQLClause.OFFSET,offSet);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep#groupBy(IExpression...)
	 */
	@Override
	public MySQLSelectOrderStep groupBy(IExpression... groupByExpr) {
		model.addExpressionList(SQLClause.GROUPBY, groupByExpr);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep#limit(IExpression)
	 */
	@Override
	public MySQLSelectOrderStep limit(IExpression limitTerm) {
		model.addClause(MySQLClause.LIMIT,limitTerm);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectOrderStep#orderBy(OrderByExpression...)
	 */
	@Override
	public MySQLSelectOrderStep orderBy(OrderByExpression... orderByExprs) {
		model.addExpressionList(SQLClause.ORDERBY, orderByExprs);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectFromStep#where(Predicate)
	 *
	 */
	@Override
	public MySQLSelectOrderStep where(Predicate pred) {
		model.addClause(SQLClause.WHERE,pred);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectFetchStep#fetch()
	 */
	@Override
	public List<ITableRow> fetch() {
		List<ITableRow> results = new ArrayList<>();
		Future <List<ITableRow>> task;
		
		/*
		 * For now this seems to work well in that it executes the SELECT
		 * in another thread. Since I don't expect to be using this for 
		 * results > 5000 rows.  I will leave it this way for now.
		 */
		if (conn != null) {
			task = RunDMLExecutor.getInstance().executeSelect(conn, model,tbDef);			
		} else {
			//ASSUME: provider != null if the connection is null
			task = RunDMLExecutor.getInstance().executeSelect(
						provider.getConnection(), model,tbDef);
		}
		
		try {
			results = task.get();
		} catch (CancellationException | ExecutionException | InterruptedException ex) {
			logger.error(ex.getMessage(), ex);
			Thread.currentThread().interrupt();
		}
			
		return results;

	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#selectStar()
	 */
	@Override
	public MySQLSelectListStep selectStar() {
		model.addClause(SQLClause.SELECT);
		model.addClause(SQLClause.SELECTSTAR);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#select()
	 */
	@Override
	public MySQLSelectListStep select() {
		model.addClause(SQLClause.SELECT);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#select()
	 */
	@Override
	public MySQLSelectFromStep select(TableDefinition tbDef) {
		model.addClause(SQLClause.SELECT);
		model.addColumnList(SQLClause.SELECTEXPR, tbDef);
		model.addClause(SQLClause.FROM, tbDef.qualifedTableName());
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#all()
	 */
	@Override
	public MySQLSelectListStep all() {
		model.addClause(SQLClause.ALL);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#distinct()
	 */
	@Override
	public MySQLSelectListStep distinct() {
		model.addClause(SQLClause.DISTINCT);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#smallResult()
	 */
	@Override
	public MySQLSelectListStep smallResult() {
		model.addClause(MySQLClause.SMALL_RESULT);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#bigResult()
	 */
	@Override
	public MySQLSelectListStep bigResult() {
		model.addClause(MySQLClause.BIG_RESULT);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#bufferResult()
	 */
	@Override
	public MySQLSelectListStep bufferResult() {
		model.addClause(MySQLClause.BUFFER_RESULT);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#selectExpression(IExpression)
	 */
	@Override
	public MySQLSelectListStep selectExpression(IExpression expr) {
		model.addExpressionList(SQLClause.SELECTEXPR, expr);
		return this;
	}

	/**
	 * @see com.bobman159.rundml.mysql.select.steps.MySQLSelectListStep#from(String, String)
	 */
	@Override
	public MySQLSelectFromStep from(String schema, String tbName) {
		model.addClause(SQLClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		return this;
	}
	
	/**
	 * Returns the SQL SELECT statement for the current statement instance
	 * @return the SELECT statement text
	 */
	public String toStmt() {
		return SQLStatementSerializer.serializeSelect(model);
	}

}
