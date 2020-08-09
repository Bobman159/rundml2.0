package com.bobman159.rundml.core.predicates.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bobman159.rundml.core.predicates.IPredicate;
import com.bobman159.rundml.core.predicates.IPredicatesList;

/**
 * Maintains a list of the predicates (WHERE, AND, OR) for an SQL statement.
 * 
 */
public class PredicatesList implements IPredicatesList {
	
	private LinkedList<IPredicate> predicateList;

	/**
	 * Define the list of predicates 
	 */
	public PredicatesList() {
		predicateList = new LinkedList<>();
	}
	
	/**
	 * Add a predicate to the current list of predicates
	 * 
	 * @param pred the predicate to add to the current list
	 */
	@Override
	public void addPredicate(IPredicate pred) {
		predicateList.add(pred);
	}
	
	/**
	 * 
	 * @return the last predicate in the current list of predicates
	 */
	@Override
	public IPredicate getLastPredicate() {
		return predicateList.getLast();
	}
	
	/**
	 * @return the list of predicates as a stream
	 */
	@Override
	public List<IPredicate> getPredicates() {
		return Collections.unmodifiableList(predicateList);
	}
}
