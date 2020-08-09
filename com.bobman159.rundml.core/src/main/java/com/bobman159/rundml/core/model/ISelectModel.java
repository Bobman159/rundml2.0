package com.bobman159.rundml.core.model;

import com.bobman159.rundml.core.model.impl.SelectClause;
import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * An SQL SELECT model definition
 */
public interface ISelectModel {

	/**
	 * Add a SELECT value to the select model
	 * @param selectExpr the value to be added
	 */
	public void addSelectExpression(ISQLType selectExpr);
	
	/**
	 * Add a list of SELECT value, value, value... to the select model
	 * @param selectExprs the list of values to be added
	 */
	public void addSelectExpression(ISQLType... selectExprs);
	
	/**
	 * Add a list of SELECT value, value, value... to the select model
	 * @param orderByEntries the list of values to be added
	 */
	public void addOrderByEntry(IOrderByEntry... orderByEntries);
	
	/***
	 * Adds an entry to the SELECT statement if it does not exist for the
	 * select clause.  Otherwise the existing entry for the clause is 
	 * replaced with the new value.
	 * @param clause the clause being added or replaced
	 * @param value the value being added or replaced
	 */
	public void putProp(SelectClause clause, Object value);

}
