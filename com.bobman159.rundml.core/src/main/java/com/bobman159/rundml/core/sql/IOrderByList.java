package com.bobman159.rundml.core.sql;

import java.util.List;

/**
 * List of Expressions and values for an SQL ORDER BY clause
 *
 */
public interface IOrderByList {
	
	/**
	 * Add an expression to the ORDER BY clause
	 * @param orderByClause the expression for the ORDER BY
	 */
	public void addOrderByClause(IOrderByEntry orderByClause);
	
	/**
	 * Add an list of expressions to the ORDER BY clause
	 * @param orderByClauses the expression for the ORDER BY
	 */
	public void addOrderByClause(IOrderByEntry[] orderByClauses);
	
	/**
	 * @return the list of Order By clauses as a non modifiable list
	 */
	public List<IOrderByEntry> getOrderBys(); 

}
