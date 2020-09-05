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

}
