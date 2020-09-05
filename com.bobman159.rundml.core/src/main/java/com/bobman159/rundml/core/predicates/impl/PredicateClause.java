package com.bobman159.rundml.core.predicates.impl;

import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Represents a predicate WHERE, AND, OR clause in an SQL statement.
 *
 */
public class PredicateClause implements IPredicate {
	
	private PredOperand predOperand;
	private ISQLCondition condition;
		
	/**
	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
	 * @param predOperand type of clause being defined
	 * @param condition the condition for the SQL predicate
	 */
	public PredicateClause(PredOperand predOperand,ISQLCondition condition) {
		this.predOperand = predOperand;
		this.condition = condition;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.types.ISQLType#getType()
	 */
	@Override
	public SQLType getType() {
		return SQLType.PREDICATE;
	}

	/**
	 * @see com.bobman159.rundml.core.predicates.IPredicate#getCondition()
	 * 
	 */
	@Override
	public ISQLCondition getCondition() {
		return this.condition;
		
	}

	/**
	 * @see com.bobman159.rundml.core.predicates.IPredicate#getPredicateOperation()
	 */
	@Override
	public PredOperand getPredicateOperation() {
		return this.predOperand;
	}

	@Override
	public void setCondition(ISQLCondition condition) {
		this.condition = condition;
	}

}
