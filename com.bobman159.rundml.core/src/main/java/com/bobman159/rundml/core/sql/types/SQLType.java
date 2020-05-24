package com.bobman159.rundml.core.sql.types;

/**
 * Enumerates the types of SQL types used for building SQL clauses, expressions and predicates
 * 
 */
public enum SQLType {

	CASE,
	
	ORDER_BY,
	
	/** A numeric constant with a number value */
	NUMERIC,
	/** A string/character constant */
	STRING,
	/** A column in a table */
	COLUMN,
	/** A parameter marker '?' value */
	PARM_MARKER,
	/** 
	 * An SQL Expression
	 */
	EXPRESSION,
	/** A SQL Predicate WHERE, AND or OR clause *
	 * 
	 */
	PREDICATE,
	/**
	 * A Database Table
	 */
	TABLE;
	/** A string expression (eg "COL01 || COL02") */
//	STRING_EXPR,
	/** A math expression addition, subtraction, multiply, divide (eg 10 + 20) */
//	MATH_EXPR;
	
}
