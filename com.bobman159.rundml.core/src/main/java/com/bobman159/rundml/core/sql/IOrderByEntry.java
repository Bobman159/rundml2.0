package com.bobman159.rundml.core.sql;

import com.bobman159.rundml.core.sql.types.ISQLType;

/**
 * An entry in an SQL ORDER BY clause
 *
 */
public interface IOrderByEntry {

	/**
	 * ORDER BY sort order values
	 */
	public enum SortOrder {ASC,DESC};	//NOSONAR
	/**
	 * ORDER BY nulls order values
	 *
	 */
	public enum NullsOrder {NULLS_FIRST,NULLS_LAST};	//NOSONAR
	
	/**
	 * 
	 * @return the value for the ORDER BY
	 */
	public ISQLType getOrderByValue();
	
	/**
	 * Set the sort order ASC or DESC 
	 * @param sortOrder the sort order
	 */
	public void setSortOrder(SortOrder sortOrder);
	
	/**
	 *
	 * @return the sort order enumeration
	 */
	public SortOrder getSortOrder();
	
	/**
	 * Set the order of null values NULLS FIRST or NULLS LAST
	 * @param nullOrder the nulls order
	 */
	public void setNullsOrder(NullsOrder nullOrder);
	
	/**
	 * 
	 * @return the nulls order enumeration
	 */
	public NullsOrder getNullsOrder();
	
	/**
	 * Specify a sort order of ASCending
	 * @return the ORDERBY entry
	 */
	public IOrderByEntry asc();
	
	/**
	 * Specify a sort order of DESCending
	 * @return the ORDER BY entry
	 */
	public IOrderByEntry desc();
	
	/**
	 * Specify a null order where nulls are FIRST
	 * @return the ORDER BY Entry
	 */
	public IOrderByEntry nullsFirst();
	
	/**
	 * Specify a null order where nulls are LAST
	 * @return the ORDER BY Entry
	 */
	public IOrderByEntry nullsLast();
}
