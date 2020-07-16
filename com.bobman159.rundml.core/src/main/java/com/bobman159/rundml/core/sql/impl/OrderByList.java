package com.bobman159.rundml.core.sql.impl;

import java.util.LinkedList;

import com.bobman159.rundml.core.sql.IOrderByEntry;
/**
 * List of the expressions in an SQL ORDER BY clause
 *
 */
public class OrderByList {
	
	private LinkedList<IOrderByEntry> orderByClauses;
	
	/**
	 * Create the list
	 */
	public OrderByList() {
		orderByClauses = new LinkedList<>();
	}
	
	/**
	 * Add an expression to the ORDER BY clause
	 * @param orderByExpr the expression for the ORDER BY
	 * @return the ORDER BY clause
	 */
	public void addOrderByClause(IOrderByEntry orderByClause) {
		orderByClauses.add(orderByClause);
	}
	
	/**
	 * @return the list of Order By clauses as a iterable
	 */
	public Iterable<IOrderByEntry> iteratable() {
		return orderByClauses;
	}
	
	/**
	 * @see java.util.LinkedList#size()
	 */
	public int size() {
		return orderByClauses.size();
	}
	
}
