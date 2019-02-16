package net.bobs.own.db.rundml.sql.builders.predicates;

/**
 * Define the predicate AND, and predicate OR and build predicate steps, 
 * the first steps in building a predicate
 *
 */
public interface IPredicateClauseStep {

	/**
	 * Create an SQL AND clause predicate
	 * @param expr - expression for the AND
	 * @return - predicate builder
	 */
	public IPredicateComparisonStep and(Object expr);

	/**
	 * Create an SQL OR clause predicate 
	 * @param expr - expression for the OR
	 * @return - predicate builder
	 */
	public IPredicateComparisonStep or(Object expr);
	
	/**
	 * Finalize the predicate clause(s) for the current predicate
	 * @return = the <code>Predicate</code> 
	*/
	public Predicate build();

}
