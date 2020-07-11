package com.bobman159.rundml.core.sql.types;

/**
 * Enumerates the types of SQL types used for building SQL clauses, expressions and predicates
 * 
 */
public enum SQLType {

	CASE,
	/** A numeric constant with a number value */
	NUMERIC,
	/** A string/character constant */
	STRING,
	/** A column in a table */
	COLUMN,
	/** A parameter marker '?' value */
	PARM_MARKER,
	/** 
	 * An SQL IExpressionFactory
	 */
	EXPRESSION,
	/** A SQL PredicateBuilder WHERE, AND or OR clause *
	 * 
	 */
	PREDICATE,
	/**
	 * A Database Table
	 */
	TABLE,
	/** And SQL condition */
	CONDITION;
	
}
