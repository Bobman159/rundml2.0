package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.model.ISQLStatement;
import com.bobman159.rundml.core.sql.IStatementSerializer;
import com.bobman159.rundml.core.sql.serialize.impl.CommonSQLClauseSerializer;
import com.bobman159.rundml.core.sql.serialize.impl.SQLTypeSerializer;

/**
 * Base definition and method for creation SELECT, INSERT,UPDATE and DELETE SQL statements
 *
 */
public abstract class AbstractStatementSerializer extends SQLTypeSerializer implements IStatementSerializer {

	public static final String GENERIC_SELECT = "GENERIC.SELECT";
	public static final String H2_SELECT = "H2.SELECT";
	public static final String MYSQL_SELECT = "MYSQL.SELECT";
	
	public static final String SELECT = "SELECT";
	
	private ISQLStatement sqlModel;
	protected StringBuilder sql;
	protected CommonSQLClauseSerializer serializer = CommonSQLClauseSerializer.getInstance();
	
	/**
	 * Creates a new AbstractStatementSerializer using the SQL statement for
	 * serialization.
	 * @param statement the SQL statement to be serialized
	 */
	public AbstractStatementSerializer(ISQLStatement statement) {
		sqlModel = statement;
		sql = new StringBuilder();
	}
	
	/**
	 * 
	 * @return the SQL statement being serialized
	 */
	public ISQLStatement getStatement() {
		return sqlModel;
	}
	
	/**
	 * Specify the SQL statement to be serialized.
	 * @param statement the statement to be serialized
	 */
	public abstract void setStatement(ISQLStatement statement);

	/**
	 * 
	 * @return the SQL statement text
	 */
	public abstract String serializeSQLStatement();
	
}
