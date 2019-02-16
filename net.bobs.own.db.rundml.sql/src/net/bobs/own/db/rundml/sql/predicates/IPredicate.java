package net.bobs.own.db.rundml.sql.predicates;

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
