package com.bobman159.rundml.core.sql.impl;

import com.bobman159.rundml.core.sql.ISQLClause;

/**
 * Defines ORDER BY clause enumerations 
 *
 */
public class OrderByEnums {
	
	/**
	 * Defines ORDER BY sort directions 
	 *
	 */
	public enum OrderBySortEnum implements ISQLClause {
		ASC("asc"),
		DESC("desc");

		
		private String orderBySort = "";
		
		private OrderBySortEnum(String orderBySort) {
			this.orderBySort = orderBySort;
		}

		/**
		 * @see com.bobman159.rundml.core.sql.ISQLClause#serializeClause()
		 */
//		@Override
		public String serializeClause() {
			return this.orderBySort;
		}
		
	}

	/**
	 * Defines ORDER BY NULLS enumerations 
	 *
	 */
	public enum OrderByNullsEnum implements ISQLClause {
		NULLS_FIRST("nulls first"),
		NULLS_LAST("nulls last");
		
		private String orderByNulls;
		
		private OrderByNullsEnum(String orderByNull) {
			this.orderByNulls = orderByNull;
		}

//		@Override
		public String serializeClause() {
			return this.orderByNulls;
		}
	}
}