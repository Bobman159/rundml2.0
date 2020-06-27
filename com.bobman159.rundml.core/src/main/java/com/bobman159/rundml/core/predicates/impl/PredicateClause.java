package com.bobman159.rundml.core.predicates.impl;

import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.sql.conditions.SQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;
import com.bobman159.rundml.core.sql.types.SQLType;

/**
 * Represents a predicate WHERE, AND, OR clause in an SQL statement.
 *
 */
public class PredicateClause implements IPredicate {
	
	private PredOperand predOperand;
	private ISQLCondition condition;
	
	/**
	 * Define a complete SQL WHERE, AND or OR clause
	 * 
	 * @param predOperand type of predicate
	 * @param condition the SQL condition for the predicate
	 * 
	 */
	public static IPredicate createPredicate(PredOperand predClause,ISQLCondition condition) {
		return new PredicateClause(predClause,condition);
	}
	
	/**
	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
	 * @param predOperand type of clause being defined
	 * @param leftPred the left side of the SQL predicate
	 */
	public static IPredicate createPredicate(PredOperand predOperand, ISQLType leftPred) {
		SQLCondition partCond = SQLCondition.createSQLCondition(leftPred);
		return new PredicateClause(predOperand,partCond);
	}
	
		
	/*
	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
	 * @param predOperand type of clause being defined
	 * @param leftPred the left side of the SQL predicate
	 */
	private PredicateClause(PredOperand predOperand,ISQLCondition condition) {
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
	
//	/**
//	 * Adds a predicate to the current chain of predicates
//	 * @param pred the predicate to be added, must not be null
//	 */
//	@Override
//	public void add(IPredicate pred) {
//		if (pred == null) {
//			throw new IllegalArgumentException("Predicate being added must not be null");
//		}
//		nextPred = pred;
//	}
//	
//	/**
//	 * 
//	 * @return the next predicate clause 
//	 */
//	@Override
//	public IPredicate nextPredicate() {
//		return this.nextPred;
//	}

}
