package com.bobman159.rundml.core.sql.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.IOrderByList;
/**
 * List of the expressions in an SQL ORDER BY clause
 *
 */
public class OrderByList implements IOrderByList {
	
	private LinkedList<IOrderByEntry> orderByClauses;
	
	/**
	 * Create the list
	 */
	public OrderByList() {
		orderByClauses = new LinkedList<>();
	}
	
	/**
	 * Add an expression to the ORDER BY clause
	 * @param orderByClause the expression for the ORDER BY
	 */
	@Override
	public void addOrderByClause(IOrderByEntry orderByClause) {
		orderByClauses.add(orderByClause);
	}
	
	/**
	 * Add an list of expressions to the ORDER BY clause
	 * @param orderByClauses the expression for the ORDER BY
	 */
	@Override
	public void addOrderByClause(IOrderByEntry[] orderByClauses) {
		for (IOrderByEntry entry: orderByClauses) {
			this.orderByClauses.add(entry);
		}
	}
	
	/**
	 * @return an unmodifiable list of Order By clauses
	 */
	@Override
	public List<IOrderByEntry> getOrderBys() {
		return Collections.unmodifiableList(orderByClauses);
	}
	
}
