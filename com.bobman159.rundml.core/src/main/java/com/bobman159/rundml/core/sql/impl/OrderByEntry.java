package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * Implementation for an SQL ORDER BY clause and the expression(s) defined in 
 * the ORDER BY
 */
public class OrderByEntry implements IOrderByEntry {
	
	private ISQLType value;
	private SortOrder sortOrder = null;
	private NullsOrder nullsOrder = null;
	
	/**
	 * Creates the ORDER BY implementation 
	 * @param value a value in the ORDER BY clause
	 */
	public OrderByEntry(ISQLType value) {
		this.value = value;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#getOrderByValue()
	 */
	@Override
	public ISQLType getOrderByValue() {
		return value;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#setSortOrder(SortOrder)
	 */
	@Override
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#getSortOrder()
	 */
	@Override
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#setNullsOrder(NullsOrder)
	 */
	@Override
	public void setNullsOrder(NullsOrder nullsOrder) {
		this.nullsOrder = nullsOrder;
	}
	
	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#getNullsOrder()
	 */
	@Override
	public NullsOrder getNullsOrder() {
		return nullsOrder;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#asc()
	 */
	@Override
	public IOrderByEntry asc() {
		sortOrder = SortOrder.ASC;
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#desc()
	 */
	@Override
	public IOrderByEntry desc() {
		sortOrder = SortOrder.DESC;
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#nullsFirst()
	 */
	@Override
	public IOrderByEntry nullsFirst() {
		nullsOrder = NullsOrder.NULLS_FIRST;
		return this;
	}

	/**
	 * @see com.bobman159.rundml.core.sql.IOrderByEntry#nullsLast()
	 */
	@Override
	public IOrderByEntry nullsLast() {
		nullsOrder = NullsOrder.NULLS_LAST;
		return this;
	}

}
