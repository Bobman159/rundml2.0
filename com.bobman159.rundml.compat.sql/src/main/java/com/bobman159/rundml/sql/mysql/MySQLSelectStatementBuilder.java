/**
 * 
 */
package com.bobman159.rundml.sql.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.exprtypes.IExpression;
import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.tabledef.TableDefinition;
import com.bobman159.rundml.sql.base.builder.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.mysql.MySQLClauses.MySQLClause;

/**
 * Define a SELECT statement that can be executed against a table in a MySQL database  
 * 
 */
public class MySQLSelectStatementBuilder extends BaseSelectStatementBuilder<MySQLSelectStatementBuilder> {
	
	private Logger logger = LogManager.getLogger(MySQLSelectStatementBuilder.class);
	private TableDefinition tbDef = null;

	/**
	 * Create a SELECT statement that may be executed against a MySQL database table
	 * @param mapper table mapping definition for the SELECT
	 */
	public MySQLSelectStatementBuilder(TableDefinition mapper) {
		super(mapper);
		this.tbDef = mapper;
		model = new SQLStatementModel();
	}
	
	/**
	 * Specifies the OFFSET clause for a LIMIT on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param offSet the offset of the row to be returned
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder offset(IExpression offSet) {
		model.addClause(MySQLClause.OFFSET,offSet);
		return this;
	}

	/**
	 * Specifies the LIMIT clause for a MySQL SELECT
	 * @param limitTerm maximum number of rows to return
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder limit(IExpression limitTerm) {
		model.addClause(MySQLClause.LIMIT,limitTerm);
		return this;
	}

	/**
	 * Specifies a SQL_SMALL_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder smallResult() {
		model.addClause(MySQLClause.SMALL_RESULT);
		return this;
	}

	/**
	 * Specifies a SQL_BIG_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder bigResult() {
		model.addClause(MySQLClause.BIG_RESULT);
		return this;
	}

	/**
	 * Specifies a SQL_BUFFER_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder bufferResult() {
		model.addClause(MySQLClause.BUFFER_RESULT);
		return this;
	}

}
