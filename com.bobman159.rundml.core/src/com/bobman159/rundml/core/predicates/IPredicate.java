package com.bobman159.rundml.core.predicates;

/**
 * Interface to define an SQL Predicate conditions.
 * 
 *
 */
public interface IPredicate {

	/**
	 * Serialize the predicate to an SQL predicate condition.
	 * 
	 * @return - the SQL predicate as a string
	 */
	public String serialize();

}
