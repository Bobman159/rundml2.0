package net.bobs.own.db.rundml.sql.builders.predicates;

import java.util.ArrayList;

import net.bobs.own.db.rundml.sql.predicates.IPredicate;

/**
 * Maintains a list of the predicates (WHERE, AND, OR) for an SQL statement.
 * 
 */
public class PredicatesList implements IPredicate {
	
	private ArrayList<IPredicate> predicateList;

	/**
	 * Define the list of predicates 
	 */
	public PredicatesList() {
		predicateList = new ArrayList<>();
	}
	
	/**
	 * Add a predicate to the current list of predicates
	 * 
	 * @param pred - the predicate to add to the current list
	 */
	public void addPredicate(IPredicate pred) {
		predicateList.add(pred);
	}
	
	/**
	 * @see net.bobs.own.db.rundml.sql.predicates.IPredicate#serialize()
	 */
	@Override
	public String serialize() {

		StringBuilder expr = new StringBuilder();
		
		for (IPredicate pred : predicateList) {
			expr.append(pred.serialize());
		}
		
		return expr.toString();
	}

}
