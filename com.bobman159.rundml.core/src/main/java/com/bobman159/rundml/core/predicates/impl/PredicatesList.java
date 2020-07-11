package com.bobman159.rundml.core.predicates.impl;

import java.util.LinkedList;

import com.bobman159.rundml.core.predicates.IPredicate;

/**
 * Maintains a list of the predicates (WHERE, AND, OR) for an SQL statement.
 * 
 */
public class PredicatesList {
	
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
	public void addPredicate(IPredicate pred) {
		predicateList.add(pred);
	}
	
	/**
	 * 
	 * @return the first predicate in the current list of predicates
	 */
	public IPredicate getFirstPredicate() {
		return predicateList.getFirst();
	}
	
	/**
	 * 
	 * @return the last predicate in the current list of predicates
	 */
	public IPredicate getLastPredicate() {
		return predicateList.getLast();
	}
	
	/**
	 * 
	 * @return current size of the predicate list
	 */
	public int size() {
		return predicateList.size();
	}

	/**
	 * @return the list of predicates as a stream
	 */
	public Iterable<IPredicate> iteratable() {
		return predicateList;
	}
}
