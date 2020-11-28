package com.bobman159.rundml.common.serialize;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.model.IStatementSerializer;
import com.bobman159.rundml.common.serialize.impl.CommonSQLClauseSerializer;
import com.bobman159.rundml.common.serialize.impl.SQLTypeSerializer;


/**
 * Base definition and method for creation SELECT, INSERT,UPDATE and DELETE SQL statements
 * This class is intended to be sub-classed when creating new SQL statement serializers.
 *
 */
public abstract class AbstractStatementSerializer extends SQLTypeSerializer implements IStatementSerializer {

	public static final String GENERIC_SELECT = "GENERIC.SELECT";
	public static final String H2_SELECT = "H2.SELECT";
	public static final String MYSQL_SELECT = "MYSQL.SELECT";
	
	private ISQLStatement sqlModel;
	protected StringBuilder sql;
	protected CommonSQLClauseSerializer serializer = CommonSQLClauseSerializer.getInstance();
	
	public AbstractStatementSerializer() {
		sql = new StringBuilder();
	}
	
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
	
}
