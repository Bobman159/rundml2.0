package com.bobman159.rundml.core.predicates;

import com.bobman159.rundml.core.predicates.impl.PredOperand;
import com.bobman159.rundml.core.sql.sql.conditions.ISQLCondition;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Interface to define an SQL PredicateBuilder conditions.
 * An IPredicate is 
 * 
 *
 */
public interface IPredicate extends ISQLType {

//	/**
//	 * Define a complete SQL WHERE, AND or OR clause
//	 * 
//	 * @param predOperand type of predicate
//	 * @param condition the SQL condition for the predicate
//	 * 
//	 */
//	public IPredicate createPredicate(IPredicate predClause,SQLCondition condition);
//	
//	/**
//	 * Defines a partial SQL WHERE, AND or OR clause with a partial SQL Condition
//	 * @param predOperand type of clause being defined
//	 * @param leftPred the left side of the SQL predicate
//	 */
//	public IPredicate createPredicate(PredOperand predOperand, ISQLType leftPred);
	
	/**
	 * 
	 * @return the defined predicate operation for the current predicate
	 */
	public PredOperand getPredicateOperation();
	
	/**
	 *
	 * @return the defined SQL condition for the current predicate
	 */
	public ISQLCondition getCondition(); 
	
	
	/**
	 * Set the SQL condition for the current predicate
	 * @param condition the condition to use for the current predicate
	 */
	public void setCondition(ISQLCondition condition);
	
//	/**
//	 * Iterates to the next predicate.
//	 * @return a reference to the next predicate, null if there are no more predicate
//	 */
//	public IPredicate nextPredicate();
//	
//	/**
//	 * Add predicate as the next predicate in the predicate clause
//	 * @param predicate the predicate to add
//	 */
//	public void add(IPredicate predicate);



}
