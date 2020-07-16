package com.bobman159.rundml.core.sql.serialize;

/**
 * Definition for task information to serialize common SQL clauses.
 * Common SQL clauses are clauses that are valid in different SQL 
 * statements.  Ex. ORDER BY in SELECT and UPDATE
 *
 */
public interface ICommonSerializationTask {
	
	/**
	 * Common SQL clause definitions
	 *
	 */
	public enum Clause {FROM,ORDERBY};	//NOSONAR
	
	/**
	 * 
	 * @return the clause to be serialized
	 */
	public Clause getClause();
	
	/**
	 * 
	 * @return the model for the clause to be serialized
	 */
	public Object getModel();

}
