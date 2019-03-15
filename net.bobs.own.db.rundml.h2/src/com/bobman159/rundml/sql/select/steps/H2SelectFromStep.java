package com.bobman159.rundml.sql.select.steps;

import java.util.List;

import com.bobman159.rundml.core.predicates.Predicate;

/**
 * Second Builder Step in the Select.
 * Next Step available: PredicateStep OR FetchStep
 *
 */
public interface H2SelectFromStep {
	
	H2SelectOrderStep where(Predicate pred);
	List<String> fetch();  

}
