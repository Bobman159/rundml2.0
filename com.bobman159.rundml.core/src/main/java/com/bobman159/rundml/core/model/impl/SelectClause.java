package com.bobman159.rundml.core.model.impl;

/**
 * Clauses for an SQL SELECT statement.
 *
 */
public enum SelectClause {
	
	SELECTEXPR,
	ALL,
	DISTINCT,
	FROM,
	WHERE,
	GROUPBY,
	HAVING,
	ORDERBY;
}
