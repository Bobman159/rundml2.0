package com.bobman159.rundml.core.predicates;

import com.bobman159.rundml.core.predicates.impl.PredicatesList;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Define the predicate AND, and predicate OR and build predicate steps, 
 * the first steps in building a predicate
 *
 */
public interface IPredicateClauseStep {

	/**
	 * Create an SQL AND clause predicate
	 * @param expr expression for the AND
	 * @return predicate builder
	 */
	public IPredicateComparisonStep and(Number expr);
	
	/**
	 * Create an SQL AND clause predicate
	 * @param expr expression for the AND
	 * @return predicate builder
	 */
	public IPredicateComparisonStep and(String expr);
	
	/**
	 * Create an SQL AND clause predicate
	 * @param expr expression for the AND
	 * @return predicate builder
	 */
	public IPredicateComparisonStep and(ISQLType expr);

	
	/**
	 * Create an SQL OR clause predicate
	 * @param expr expression for the OR
	 * @return predicate builder
	 */
	public IPredicateComparisonStep or(Number expr);
	
	/**
	 * Create an SQL OR clause predicate
	 * @param expr expression for the OR
	 * @return predicate builder
	 */
	public IPredicateComparisonStep or(String expr);
		
	/**
	 * Create an SQL OR clause predicate 
	 * @param expr expression for the OR
	 * @return predicate builder
	 */
	public IPredicateComparisonStep or(ISQLType expr);
	
	/**
	 * Finalize the predicate clause(s) for the current predicate
	 * @return the <code>PredicateBuilder</code> 
	*/
	public PredicatesList build();

}
