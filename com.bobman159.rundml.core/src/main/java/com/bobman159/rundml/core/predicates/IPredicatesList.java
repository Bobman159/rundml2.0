package com.bobman159.rundml.core.predicates;

import java.util.List;

/**
 * Defines a list of Predicate clauses
 *
 */
public interface IPredicatesList {

	/**
	 * Add a predicate to the current list of predicates
	 * @param pred the predicate to add to the current list
	 */
	public void addPredicate(IPredicate pred);
	
	/**
	 * 
	 * @return the last predicate in the current list of predicates
	 */
	public IPredicate getLastPredicate();
	
	/**
	 * @return an unmodifiable list of predicates 
	 */
	public List<IPredicate> getPredicates();
	
}
