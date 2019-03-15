package com.bobman159.rundml.h2.sql.builder.stmts;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.compat.builder.select.IFetchableResults;
import com.bobman159.rundml.compat.builder.select.SelectClauses.SelectClause;
import com.bobman159.rundml.compat.model.SQLStatementModel;
import com.bobman159.rundml.compat.model.SQLStatementSerializer;
import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.predicates.Predicate;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.core.util.RunDMLUtils;
import com.bobman159.rundml.h2.sql.builder.stmts.H2SelectClauses.H2SelectClause;
import com.bobman159.rundml.jdbc.pool.DefaultConnectionProvider;
import com.bobman159.rundml.sql.select.steps.H2SelectFromStep;
import com.bobman159.rundml.sql.select.steps.H2SelectListStep;
import com.bobman159.rundml.sql.select.steps.H2SelectOrderStep;

/**
 * Define a SELECT statement that can be executed against an H2 database.
 *
 */
public class H2SelectStatement implements  H2SelectListStep,
										   H2SelectFromStep,
										   H2SelectOrderStep,
										   IFetchableResults {

	private Logger logger = LogManager.getLogger(H2SelectStatement.class);
	private SQLStatementModel model = new SQLStatementModel();
	private SQLStatementSerializer serializer = null;
	private Connection conn = null;
	private DefaultConnectionProvider provider = null;
	private TableDefinition tbDef = null;
	
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
		if (serializer != null) {
			serializer = null;
		}
 		model.addClause(SelectClause.SELECT);
		model.addClause(SelectClause.SELECTSTAR);
		return this;
	}
	
	/** Specify the start of a SELECT statement
	 * 
	 */
	@Override
	public H2SelectListStep select() {
		model.addClause(SelectClause.SELECT);
		return this;
	}
	
	/**
	 * Specify a SELECT statement using a <code>TableDefinition</code>
	 * Generates a "SELECT column-name [,] FROM schema.tbName clause
	 */
	@Override
	public H2SelectFromStep select(TableDefinition tbDef) {
		
		model.addClause(SelectClause.SELECT);
		this.tbDef = tbDef;
		String csvList = RunDMLUtils.csvList(tbDef.columns());
		
		model.addClause(SelectClause.SELECTEXPR,csvList);
//		tbDef.columns().forEach(col -> {
//			model.addClause(SelectClause.SELECTEXPR,col);
//		});
		model.addClause(SelectClause.FROM,tbDef.qualifedTableName());
		
		return this;
	}
	
	
	
	/**
	 * Specify the DISTINCT clause for the SELECT statement.
	 */
	@Override
	public H2SelectListStep distinct() {
		model.addClause(SelectClause.DISTINCT);
		return this;
	}
	
	/**
	 * Specify the ALL clause for the SELECT statement
	 */
	@Override
	public H2SelectListStep all() {
		model.addClause(SelectClause.ALL);
		return this;
	}
	
	/**
	 * Add an expression to be selected for the SELECT statement
	 * @param - an <code>IExpression</code> 
	 * 
	 * @see com.rob159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectListStep selectExpression(IExpression expr) {
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
		model.addClause(SelectClause.FROM,RunDMLUtils.qualifiedTbName(schema, tbName));
		return this;
	}
	
	/**
	 * Specify the WHERE clause for the SELECT statement to 
	 * return table rows matching the Predicate condition.
	 * 
	 * @param - the SQL predicate
	 * @see com.rob159.rundml.core.predicates.Predicate
	 */
	@Override
	public H2SelectStatement where(Predicate pred) {
		model.addClause(SelectClause.WHERE,pred);
		return this;
	}
	
	/**
	 * Specify an ORDER BY clause to sort the result rows from the 
	 * SELECT statement
	 * 
	 * @param - an expression 
	 * @see com.rob159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectStatement orderBy(IExpression orderByExpr) {
		model.addClause(SelectClause.ORDERBY,orderByExpr);
		return this;		
	}
	
	/**
	 * Specify a GROUP BY clause for the SELECT statement.
	 * @param - an expression 
	 * @see com.rob159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectStatement groupBy(IExpression groupByExpr) {
		model.addClause(SelectClause.GROUPBY,groupByExpr);
		return this;
	}
		
	/**
	 * Specify a HAVING clause for the SELECT statement.
	 * @param - an expression 
	 * @see com.rob159.rundml.core.exprtypes.IExpression
	 */
	@Override
	public H2SelectStatement having(IExpression havingExpr) {
		model.addClause(SelectClause.HAVING,havingExpr);
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
		model.addClause(H2SelectClause.TOP,limitTerm);
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
		serializer = new SQLStatementSerializer(model);
		return serializer.serialize();
	}
	
}
