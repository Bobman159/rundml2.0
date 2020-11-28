/**
 * 
 */
package com.bobman159.rundml.sql.mysql;

import java.util.logging.LogManager;

import org.apache.logging.log4j.Logger;

import com.bobman159.rundml.common.serialize.AbstractStatementSerializer;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.sql.builders.impl.GenericSelectBuilder;
import com.bobman159.rundml.sql.factory.SQLStatementBuilderFactory;

/**
 * Define a SELECT statement that can be executed against a table in a MySQL database  
 * 
 */
public class MySQLSelectBuilder extends GenericSelectBuilder<MySQLSelectBuilder> {
	
	private static Logger logger = LogManager.getLogger(MySQLSelectBuilder.class);

	/**
	 * Create a SELECT statement that may be executed against a MySQL database table
	 */
	public MySQLSelectBuilder() {
		super((IMySQLSelectStatement) SQLStatementBuilderFactory.getInstance().
				createSelectModel(AbstractStatementSerializer.MYSQL_SELECT));
	}
	
	/**
	 * Specifies the OFFSET clause for a LIMIT on a MYSQL select
	 * Refer to MySQL SELECT documentation full documentation
	 * @param offSet the offset of the row to be returned
	 * @return an instance of this builder
	 */
	public MySQLSelectBuilder offset(ISQLType offSet) {
		castToMySQLSelect().addOffset(offSet);
		return this;
	}

	/**
	 * Specifies the LIMIT clause for a MySQL SELECT
	 * @param limitTerm maximum number of rows to return
	 * @return an instance of this builder
	 */
	public MySQLSelectBuilder limit(ISQLType limitTerm) {
		castToMySQLSelect().addLimit(limitTerm);
		return this;
	}

	/**
	 * Specifies a SQL_SMALL_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectBuilder smallResult() {
		castToMySQLSelect().setSmallResult();
		return this;
	}

	/**
	 * Specifies a SQL_BIG_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectBuilder bigResult() {
		castToMySQLSelect().setBigResult();
		return this;
	}

	/**
	 * Specifies a SQL_BUFFER_RESULT clause for a MySQL SELECT.  
	 * Refer to MySQL documentation for more information
	 * @return an instance of this builder
	 */
	public MySQLSelectBuilder bufferResult() {
		castToMySQLSelect().setBufferResult();
		return this;
	}
	
	private IMySQLSelectStatement castToMySQLSelect() {
		if (model instanceof IMySQLSelectStatement) {
			return (IMySQLSelectStatement) model;
		} else {
			throw new IllegalArgumentException("model is not an istance of IMySQLSelectStatement");
		}
	}

}
