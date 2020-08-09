/**
 * 
 */
package com.bobman159.rundml.sql.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.core.model.SQLStatementModel;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.sql.builders.impl.BaseSelectStatementBuilder;
import com.bobman159.rundml.sql.mysql.MySQLClauses.MySQLClause;

/**
 * Define a SELECT statement that can be executed against a table in a MySQL database  
 * 
 */
public class MySQLSelectStatementBuilder extends BaseSelectStatementBuilder<MySQLSelectStatementBuilder> {
	
	private Logger logger = LogManager.getLogger(MySQLSelectStatementBuilder.class);

	/**
	 * Create a SELECT statement that may be executed against a MySQL database table
	 */
	public MySQLSelectStatementBuilder() {
		super();
		model = new SQLStatementModel();
	}
	
	/**
	 * Specifies the OFFSET clause for a LIMIT on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param offSet the offset of the row to be returned
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder offset(ISQLType offSet) {
		model.addClause(MySQLClause.OFFSET,offSet);
		return this;
	}

	/**
	 * Specifies the LIMIT clause for a MySQL SELECT
	 * @param limitTerm maximum number of rows to return
	 * @return an instance of this builder
	 */
	public MySQLSelectStatementBuilder limit(ISQLType limitTerm) {
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
