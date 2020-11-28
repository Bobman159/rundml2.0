package com.bobman159.rundml.common.serialize;

import com.bobman159.rundml.common.model.ISQLStatement;
import com.bobman159.rundml.common.model.ISelectStatement;
import com.bobman159.rundml.common.serialize.impl.CommonSQLClauseSerializer;

/**
 * Template definition for the SQL SELECT statement serialization algorithm.
 * This class should be sub classed when creating new SQL SELECT statement serializers.
 *
 */
public abstract class AbstractSelectSerializer  extends AbstractStatementSerializer {
	protected ISelectStatement model;
	
	public AbstractSelectSerializer() {
		super();
	}
	
	/**
	 * Create a new AbstractSelectSerializer using the SQL statement for 
	 * serialization.
	 * @param statement the SELECT to be serialized.
	 */
	public AbstractSelectSerializer(ISelectStatement statement) {

		super(statement);
		model = (ISelectStatement) super.getStatement();

	}
	
	/**
	 * Serialize the first portion of a SELECT statement
	 * Subclasses of <code>AbstractSelectSerializer</code> should override 
	 * this method for DBMS (dialect) specific syntax or change the order in 
	 * which the SQL SELECT syntax is created.
	 * @return the SELECT statement SQL text
	 */
	public abstract String serializeSelect();
	
	/**
	 * Serialize the second portion of a SELECT statement starting with WHERE
	 * Subclasses of <code>AbstractSelectSerializer</code> should override 
	 * this method for DBMS (dialect) specific syntax or change the order in 
	 * which the SQL SELECT syntax is created.
	 * @return the WHERE clause and following SQL clause(s) in a SELECT
	 */
	public abstract String serializeWhere();
	
	@Override
	public String serialize() {
		throw new UnsupportedOperationException("Base class does not support this method");
	}
	
	/**
	 * Serialize the SELECT and select expresssion list from the statement
	 */
	protected void select() {
		sql.append("select ");
	}
	
	protected void distinctAll() {
		
		if (model.hasDistinct()) {
			sql.append(" distinct ");
		}
		
		if (model.hasAll()) {
			sql.append(" all ");
		}
		
	}
	
	protected void selectExpressions() {
		sql.append(serializeSQLTypeList(model.getSelectExpressions()));
		sql.append(" ");
	}
	
	
	/**
	 * Serialize the FROM clause and table name from the statement
	 */
	protected void from() {
		sql.append(CommonSQLClauseSerializer.getInstance().serializeFrom(model.getFrom()));
	}
	
	/**
	 * Serialize the WHERE clause 
	 */
	protected void wherePredicates() {
		if (model.getWhere() != null) {
			sql.append(" ");
			sql.append(serializePredicatesList(model.getWhere()));
		}
	}
	
	/**
	 * Serialize the GROUP BY clause and the optional HAVING clause 
	 * and the HAVING conditions from the statement and its expressions 
	 * from the statement.
	 */
	protected void groupByHaving() {
		if (model.getGroupByValues() != null) {
			sql.append(" group by ");
			sql.append(serializeSQLTypeList(model.getGroupByValues()));
		}
		
		if (model.getHavingPredicates() != null) {
			sql.append(" ");
			sql.append(serializePredicatesList(model.getHavingPredicates()));
		}
	}
	
	/**
	 * Serialize the ORDER BY clause and it's operands from the statement.
	 */
	protected void orderBy() {
		if (model.getOrderBys() != null) {
			sql.append(" ");
			sql.append(CommonSQLClauseSerializer.getInstance().serializeOrderBy(model.getOrderBys()));
		}
	}

}
