package com.bobman159.rundml.core.model;
/**
 * Definition(s) for SQL SELECT statement
 *
 */

import com.bobman159.rundml.core.sql.IOrderByEntry;
import com.bobman159.rundml.core.sql.impl.OrderByList;

/** Base Model class for SELECT sql statements 
 * 
 */
public class BaseSelectModel {
	
	private static BaseSelectModel model;
	private OrderByList orderBys = new OrderByList();
	
	/**
	 * 
	 * @return the current instance of this object
	 */
	public static BaseSelectModel getInstance() {
		if (model == null) {
			model = new BaseSelectModel();
		}
		return model;
	}
	

	/**
	 * Add an entry to the SQL ORDER BY clause
	 * @param entry - ORDER BY entry
	 */
	public void addOrderByEntry(IOrderByEntry entry) {
		orderBys.addOrderByClause(entry);
	}
	

}
